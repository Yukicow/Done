package hello.practice.web.item;


import hello.practice.domain.item.DeliveryCode;
import hello.practice.domain.item.Item;
import hello.practice.domain.item.form.AddForm;
import hello.practice.domain.item.form.UpdateForm;
import hello.practice.domain.item.service.ItemService;
import hello.practice.domain.member.Member;
import hello.practice.domain.member.service.MemberService;
import hello.practice.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final MemberService memberService;

    @ModelAttribute("deliveryCodes")
    public DeliveryCode[] deliveryCode(){
        DeliveryCode[] deliveryCodes = DeliveryCode.values();
        return deliveryCodes;
    }


    @GetMapping("/list")
    public String itemList(Model model) {
        model.addAttribute("items", itemService.findAllPub());
        return "/item/list";
    }

    @GetMapping("/add")
    public String addItemForm(@ModelAttribute AddForm addItemForm){
        return "/item/add-form";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute AddForm addForm, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model){

        if(addForm.getPrice() != null && addForm.getQuantity() != null) {
            long result = addForm.getPrice() * addForm.getQuantity();
            if ( result < 10000) {
                bindingResult.reject("totalPriceMin", "최소 상품 등록 가격(수량*가격)은 1만원 이상이어야 합니다.");
            }
        }

        if(bindingResult.hasErrors()){
            return "/item/add-form";
        }

        Item item = addFormToItem(addForm);
        itemService.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        return "redirect:/item/{itemId}";
    }

    @GetMapping("/{itemId}")
    public String itemDetail(@PathVariable Long itemId, Model model, HttpServletRequest request){

        Item item = itemService.findById(itemId);

        if(!item.getPub()){
            if(checkAccessRight(request, item)){
                model.addAttribute("item", item);
                return "/item/detail";
            }else{
                model.addAttribute("noRight", "noRight");
                model.addAttribute("items", itemService.findAllPub());
                return "/item/list";
            }
        }

        model.addAttribute("item", item);
        return "/item/detail";
    }


    @GetMapping("/update/{itemId}")
    public String updateItemForm(@PathVariable Long itemId, Model model, HttpServletRequest request){

        Item item = itemService.findById(itemId);

        if(checkAccessRight(request, item)){
            model.addAttribute("updateForm", item);
            return "/item/update-form";
        }else{
            model.addAttribute("noRight", "noRight");
            model.addAttribute("item", item);
            return "/item/detail";
        }
    }



    @PostMapping("/update/{itemId}")
    public String updateItem(@PathVariable Long itemId, @Validated @ModelAttribute UpdateForm updateParam,
                             BindingResult bindingResult, Model model){

        if(updateParam.getPrice() != null && updateParam.getQuantity() != null) {
            long result = updateParam.getPrice() * updateParam.getQuantity();
            if ( result < 10000) {
                bindingResult.reject("totalPriceMin", "최소 상품 등록 가격(수량*가격)은 1만원 이상이어야 합니다.");
            }
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("item", itemService.findById(itemId));
            model.addAttribute("updateForm", updateParam);
            return "/item/update-form";
        }

        itemService.updateItem(itemId, updateParam);
        return "redirect:/item/{itemId}";
    }

    private static Item addFormToItem(AddForm addForm) {
        Item item = new Item();
        item.setItemName(addForm.getItemName());
        item.setWriterId(addForm.getWriterId());
        item.setPrice(addForm.getPrice());
        item.setQuantity(addForm.getQuantity());
        item.setDeliveryCode(addForm.getDeliveryCode());
        item.setPub(addForm.getPub());
        return item;
    }

    private boolean checkAccessRight(HttpServletRequest request, Item item) {
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Member member = memberService.findById(memberId);
        log.info("WriterId = {}, memberLoginId = {}", item.getWriterId(), member.getLoginId());
        return item.getWriterId().equals(member.getLoginId());
    }
}
