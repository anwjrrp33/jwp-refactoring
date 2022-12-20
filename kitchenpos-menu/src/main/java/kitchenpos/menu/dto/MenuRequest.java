package kitchenpos.menu.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuProducts;
import kitchenpos.product.domain.Product;

public class MenuRequest {

    private String name;

    private BigDecimal price;

    private Long menuGroupId;

    private List<MenuProductRequest> menuProducts;

    public MenuRequest() {}

    private MenuRequest(String name, BigDecimal price, Long menuGroupId, List<MenuProductRequest> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public static MenuRequest of(String name, BigDecimal price, Long menuGroupId, List<MenuProductRequest> menuProducts) {
        return new MenuRequest(name, price, menuGroupId, menuProducts);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductRequest> getMenuProducts() {
        return menuProducts;
    }

    public List<Long> getMenuProductIds() {
        return menuProducts.stream()
            .map(MenuProductRequest::getProductId)
            .collect(Collectors.toList());
    }

    public Menu toMenu() {
        List<MenuProduct> menuProducts = getMenuProducts().stream()
            .map(MenuProductRequest::toMenuProduct)
            .collect(Collectors.toList());
        return Menu.of(name, price, menuGroupId, MenuProducts.from(menuProducts));
    }

    private MenuProducts createMenuProducts(List<Product> products) {
        return MenuProducts.from(menuProducts.stream()
            .map(menuProductRequest -> menuProductRequest.toMenuProduct(products))
            .collect(Collectors.toList())
        );
    }
}