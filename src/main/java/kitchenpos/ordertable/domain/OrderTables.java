package kitchenpos.ordertable.domain;

import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import kitchenpos.common.constant.ErrorCode;
import org.springframework.util.CollectionUtils;

public class OrderTables {

    private static final int MIN_SIZE = 2;

    @OneToMany(mappedBy = "tableGroup", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderTable> values;

    protected OrderTables() {}

    private OrderTables(List<OrderTable> orderTables) {
        validateOrderTableIsEmpty(orderTables);
        validateOrderTableMinSize(orderTables);
        this.values = orderTables;
    }

    public static OrderTables from(List<OrderTable> orderTables) {
        return new OrderTables(orderTables);
    }

    private void validateOrderTableIsEmpty(List<OrderTable> orderTables) {
        if (CollectionUtils.isEmpty(orderTables)) {
            throw new IllegalArgumentException(ErrorCode.ORDER_TABLE_NOT_EMPTY.getErrorMessage());
        }
    }

    private void validateOrderTableMinSize(List<OrderTable> orderTables) {
        if (orderTables.isEmpty() || orderTables.size() < MIN_SIZE) {
            throw new IllegalArgumentException(ErrorCode.ORDER_TABLE_MIN_SIZE.getErrorMessage());
        }
    }

    public void ungroupOrderTables() {
        values.forEach(OrderTable::ungroup);
    }

    public void registerTableGroup(Long tableGroupId) {
        values.forEach(orderTable -> orderTable.registerTableGroup(tableGroupId));
    }

    public List<OrderTable> getOrderTables() {
        return Collections.unmodifiableList(values);
    }
}
