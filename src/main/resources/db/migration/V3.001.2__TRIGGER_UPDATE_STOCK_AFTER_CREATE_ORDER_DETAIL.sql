CREATE OR REPLACE FUNCTION updateStockAfterCreateOrderDetail()
	RETURNS TRIGGER AS
$$
BEGIN
	UPDATE PRODUCT_DETAILS PRO
	SET STOCK = PRO.STOCK - ABS(NEW.QUANTITY)
	WHERE PRO.ID = NEW.PRODUCT_DETAIL_ID AND NEW.STATUS_ORDER_DETAIL = 1;
	RETURN NEW;
END;
$$
	LANGUAGE 'plpgsql';
CREATE TRIGGER UPDATE_STOCK_AFTER_CREATE_ORDER_DETAIL
	AFTER INSERT
	ON ORDER_DETAILS
	FOR EACH ROW
EXECUTE FUNCTION updateStockAfterCreateOrderDetail()