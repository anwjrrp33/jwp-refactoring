package kitchenpos.menugroup.acceptance;

import static kitchenpos.menugroup.acceptance.MenuGroupRestAssured.메뉴_그룹_목록_조회_요청;
import static kitchenpos.menugroup.acceptance.MenuGroupRestAssured.메뉴_그룹_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.AcceptanceTest;
import kitchenpos.menugroup.dto.MenuGroupRequest;
import kitchenpos.menugroup.dto.MenuGroupResponse;
import kitchenpos.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("메뉴 그룹 관련 인수 테스트")
public class MenuGroupAcceptanceTest extends AcceptanceTest {

    private MenuGroupRequest 요청_피자;
    private MenuGroupRequest 요청_중식;
    private MenuGroupResponse 응답_피자;
    private MenuGroupResponse 응답_중식;

    @BeforeEach
    public void setUp() {
        super.setUp();
        요청_피자 = MenuGroupRequest.from("피자");
        요청_중식 = MenuGroupRequest.from("양식");
    }

    @DisplayName("메뉴 그룹을 생성한다.")
    @Test
    void createMenuGroup() {
        // when
        ExtractableResponse<Response> response = 메뉴_그룹_생성_요청(요청_피자);

        // then
        메뉴_그룹_생성됨(response);
    }

    @DisplayName("메뉴 그룹 목록을 조회한다.")
    @Test
    void findAllMenuGroup() {
        // given
        응답_피자 = 메뉴_그룹_생성_요청(요청_피자).as(MenuGroupResponse.class);
        응답_중식 = 메뉴_그룹_생성_요청(요청_중식).as(MenuGroupResponse.class);

        // when
        ExtractableResponse<Response> response = 메뉴_그룹_목록_조회_요청();

        // then
        메뉴_그룹_목록_응답됨(response);
        메뉴_그룹_목록_확인됨(response, Arrays.asList(응답_피자.getId(), 응답_중식.getId()));
    }

    private void 메뉴_그룹_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private void 메뉴_그룹_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 메뉴_그룹_목록_확인됨(ExtractableResponse<Response> response, List<Long> menuGroupIds) {
        List<Long> resultIds = response.jsonPath().getList(".", Product.class)
            .stream()
            .map(Product::getId)
            .collect(Collectors.toList());

        assertThat(resultIds).containsAll(menuGroupIds);
    }
}
