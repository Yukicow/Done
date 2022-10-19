package hello.practice.domain.item.resolver;

import hello.practice.domain.item.DeliveryCode;
import hello.practice.domain.item.Item;
import hello.practice.domain.member.login.Login;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AddItemArgumentResolver implements HandlerMethodArgumentResolver {


    private ModelAttributeMethodProcessor modelAttributeMethodProcessor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean assignableFrom = parameter.getParameterType().isAssignableFrom(Item.class);

        return hasParameterAnnotation && assignableFrom;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest) webRequest.getNativeRequest();


        String itemName = httpRequest.getParameter("itemName");
        String writerId = httpRequest.getParameter("writerId");
        String _price = httpRequest.getParameter("price");
        String _quantity = httpRequest.getParameter("quantity");
        String _deliverCode = httpRequest.getParameter("deliverCode");
        String _pub = httpRequest.getParameter("pub");

        Integer price = Integer.valueOf(_price);
        Integer quantity = Integer.valueOf(_quantity);
        DeliveryCode deliveryCode = DeliveryCode.valueOf(_deliverCode);
        Boolean pub = Boolean.valueOf(_pub);

        return new Item(itemName, writerId,price, quantity, deliveryCode, pub);
    }
}
