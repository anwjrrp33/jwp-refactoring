package kitchenpos.application;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import kitchenpos.dao.ProductDao;
import kitchenpos.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("상품 관련 비즈니스 테스트")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void createProduct() {
        // given
        Product product = new Product(1L, "잔치국수", BigDecimal.valueOf(3_000));
        when(productDao.save(product)).thenReturn(product);

        // when
        Product result = productService.create(product);

        // then
        assertAll(
            () -> assertThat(result.getId()).isEqualTo(product.getId()),
            () -> assertThat(result.getName()).isEqualTo(product.getName()),
            () -> assertThat(result.getPrice()).isEqualTo(product.getPrice())
        );
    }

    @DisplayName("상품의 가격이 null이면 예외가 발생한다.")
    @Test
    void createProductNullPriceException() {
        // given
        Product product = new Product(1L, "잔치국수", null);

        // when & then
        assertThatThrownBy(() -> productService.create(product))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 0원보다 작으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = { -1, -1000, -10000 })
    void crateProductUnderZeroPriceException(int price) {
        // given
        Product product = new Product(1L, "잔치국수", BigDecimal.valueOf(price));

        // when & then
        assertThatThrownBy(() -> productService.create(product))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 목록을 조회할 수 있다.")
    @Test
    void findAllProducts() {
        // given
        Product product = new Product(1L, "잔치국수", BigDecimal.valueOf(3_000));
        when(productDao.findAll()).thenReturn(Arrays.asList(product));

        // when
        List<Product> result = productService.list();

        // then
        assertThat(result).hasSize(1)
            .containsExactly(product);
    }
}
