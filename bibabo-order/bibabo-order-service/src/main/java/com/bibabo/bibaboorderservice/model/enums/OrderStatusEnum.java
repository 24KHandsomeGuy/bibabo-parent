package com.bibabo.bibaboorderservice.model.enums;

public enum OrderStatusEnum {

    /**
     * 订单状态
     */
    NEW(1001, "新建"),
    WAIT_FOR_PAY(1002, "待付款"),
    AMOUNT_PAID(1003, "已付款"),

    ORDER_FILL(2000, "订单虚拟组套填充"),
    WAIT_FOR_VERIFY(2001, "等待审核"),
    VERIFY_SUCCESS(2002, "审核通过"),
    VERIFY_FAILED(2003, "审核不通过"),
    WAIT_FOR_SPLIT(2004, "待拆单"),
    SPLIT_SUCCESS(2005, "已拆单"),
    PUSH_PRESALE(2006, "推预售"),
    ORDER_PAUSE(2007, "暂停"),
    // 2008 改为预留位，目前未使用
    ORDER_PROMISE(2008, "订单生效时间"),
    ALLOCATE_DELIVERY(2009, "分配承运商"),
    ALLOCATE_WARE(2010, "分配ERP库存"),
    GENERATE_EXPRESS_BILL(2011, "生成面单"),
    SEND_WMS(2012, "下发wms"),

    WMS_RECEIVED(3001, "订单导入仓库"),
    WMS_ALLOC_INVENTROY(3002, "库存分配"),
    WMS_INVENTROY_SYS_SHORT(3003, "系统报缺"),
    WMS_ORDER_GATHER(3004, "订单汇总"),
    WMS_ORDER_PICKING(3005, "拣货完成"),
    WMS_ORDER_QUALITY_CHECK(3006, "质检确认"),
    WMS_INVENTROY_SHORT(3007, "实物报缺"),
    WMS_PACKAGE_CHECK(3008, "包装确认"),
    WMS_DELIVERIED(3009, "交接出库"),
    CUSTOMS_DECLARATION(3010, "海关-报关"),
    CUSTOMS_LIQUIDATION(3011, "海关-清关中"),
    CUSTOMS_CLEARANCE(3012, "海关-已清关"),
    CUSTOMS_CLEARANCE_EXCP(3013, "海关-清关异常"),
    TMS_CARRIER_RECEIVE(4000, "快递签收"),
    TMS_DELIVERY_CENTER_IN(4001, "入分拨中心"),
    TMS_STATION_ALLOCATED(4002, "站点已分配"),
    TMS_DELIVERY_CENTER_OUT(4003, "出分拨中心"),
    TMS_ARRIVED_STATION(4004, "订单到达站点"),
    TMS_COURIER_ALLOCATE(4005, "快递员分配"),
    TMS_STATION_OUT(4006, "订单出站点"),
    TMS_ACHIEVEMENT(4007, "妥投"),
    TMS_REJECTION(4008, "拒收"),
    TMS_FAILED(4009, "配送失败"),
    TMS_DELAY(4010, "延期配送"),
    TMS_ORDER_BACK(4011, "拒收回大库"),
    TMS_ORDER_LOST(4012, "丢包"),
    ORDER_CANCEL(9001, "取消"),
    ORDER_RETURN_APPLY(9003, "退货申请"),
    ORDER_RETURN_ACCEPT(9004, "退货申请确认"),
    ORDER_FETCH(9005, "待快递上门取货"),
    ORDER_FETCH_FINISH(9006, "取货完成"),
    ORDER_BACK_WMS(9007, "到货登记"),
    ORDER_REFUND_APPLY(9008, "已申请退款"),
    ORDER_REFUND_ACCEPT(9009, "退款审核通过"),
    ORDER_REFUND_FINISH(9010, "已退款"),

    SALES_IN_WAREHOUSE(1, "销售订单进入WMS"),
    SALES_INV_ALLOCATED(2, "销售订单库存占用"),
    SALES_SUM_UP(3, "销售订单汇总拣选"),
    SALES_PICK(4, "销售订单完成拣货"),
    SALES_QC_PACK(5, "销售订单质检"),
    SALES_WEIGH(6, "销售订单称重"),
    SALES_CUTOVER(7, "销售订单批次交接"),
    SALES_SYNCTO_TMS(13, "销售sync到TMS系统"),
    SALES_REAL_SHORT(9, "销售订单实物报缺"),
    SALES_CANCEL(10, "销售订单取消"),
    SALES_SHORT_QC(11, "销售订单报缺重捡"),
    SALES_HOLD(12, "销售订单等待列表"),

    EVALUATION_SUCCESS(10001, "已评价");

    private int status;
    private String statusName;

    private OrderStatusEnum(int status, String statusName) {
        this.status = status;
        this.statusName = statusName;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    /**
     * 取得订单状态名称
     *
     * @param code 订单状态
     * @return string
     */
    public static String getOrderStatusName(int code) {

        for (OrderStatusEnum os : OrderStatusEnum.values()) {

            if (os.getStatus() == code) {
                return os.getStatusName();
            }
        }
        return "";
    }
}
