CREATE OR REPLACE FUNCTION updateStockAfterUpdateOrderDetailStatus()
	RETURNS TRIGGER AS
$$
BEGIN 
	UPDATE PRODUCT_DETAILS PRO
	SET STOCK = CASE
					WHEN OLD.STATUS_ORDER_DETAIL IN (2,4) AND NEW.STATUS_ORDER_DETAIL IN (3,5) THEN PRO.STOCK - ABS(NEW.QUANTITY)
                 
					WHEN OLD.STATUS_ORDER_DETAIL IN (1,2,4) AND NEW.STATUS_ORDER_DETAIL IN (0,6,7) THEN PRO.STOCK + ABS(NEW.QUANTITY)
				ELSE PRO.STOCK
				END
	WHERE PRO.ID = NEW.PRODUCT_DETAIL_ID; 
	RETURN NEW; 
END;
$$
	LANGUAGE 'plpgsql';
CREATE TRIGGER UPDATE_STOCK_AFTER_UPDATE_ORDER_DETAIL_STATUS
	AFTER UPDATE OF STATUS_ORDER_DETAIL
	ON ORDER_DETAILS
	FOR EACH ROW
EXECUTE FUNCTION updateStockAfterUpdateOrderDetailStatus()