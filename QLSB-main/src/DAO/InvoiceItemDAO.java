package DAO;

import java.util.List;
import model.InvoiceItem;

public interface InvoiceItemDAO extends GenericDAO<InvoiceItem> {
    List<InvoiceItem> findByType(String Type);
    boolean addInvoiceItem(InvoiceItem item);
}
