/**
 *
 * @author: micheal abobade
 * @email: pagims2003@yahoo.com
 * @mobile: 234-8065-711-043
 * @date: 2016-07-17
 */
package com.pharm.menu;

import java.io.Serializable;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "pgeNav")
@SessionScoped
public class PageNavigation implements Serializable {

    private String page;
    private String titlePane;

    public PageNavigation() {

        setPage("/templates/common/content.xhtml");

    }

    public void inventory() {

        setPage("/Pages/inventory/inventory.xhtml");
        setTitlePane("Inventory");

    }

    public void item() {

        setPage("/Pages/inventory/item.xhtml");
        setTitlePane("Add Item");

    }

    //display bio-data page
    public void account_class() {

        setPage("pages/configacct/class_of_account.xhtml");
        setTitlePane("Class Of Account Configuration Template");

    }

    public void create_branch() {

        setPage("pages/configbranch/create_branch.xhtml");
        setTitlePane("Create Branch -  Configuration Template");

    }

    public void create_currency() {

        setPage("pages/configacct/currency_types.xhtml");
        setTitlePane("Create Currency -  Configuration Template");

    }

    public void create_bra_ledger() {

        setPage("pages/configbranch/bra_ledger_account.xhtml");
        setTitlePane("Create Branch Ledger -  Configuration Template");

    }

    //chart of account configuration...
    public void account_chart() {

        setPage("pages/configacct/chart_accounts.xhtml");
        setTitlePane("Chart Of Account Configuration Template");

    }

    public void test_imageUpload() {

        setPage("pages/customer/test_upload.xhtml");
        // setTitlePane("Account Category Configuration - Template");

    }

    public void account_category() {

        setPage("/Pages/configacct/account_category.xhtml");
        setTitlePane("Account Category Configuration - Template");

    }

    public void dynamic_images() {

        setPage("pages/customer/test_dynamic_image_loading.xhtml");
        setTitlePane("Test dynamic image loading - Template");

    }

    public void pending_aprv_account() {

        setPage("pages/customer/approve_customer_account.xhtml");
        setTitlePane("Account Pending Approval - Template");

    }

    public void teller_repo() {

        setPage("pages/report/teller_callover.xhtml");
        setTitlePane("CUSTOMER ACCOUNT CREATION - Template");

    }

    public void bio_data() {

        setPage("pages/customer/customerX.xhtml");
        setTitlePane("CUSTOMER ACCOUNT CREATION - Template");

    }

    public void book_deposit() {

        setPage("pages/fd/book_fd.xhtml");
        setTitlePane("Fixed Deposit - Template");

    }

    public void approve_deposit() {

        setPage("pages/fd/approve_fd.xhtml");
        setTitlePane("Fixed Deposit - Template");

    }

    public void book_facility() {

        setPage("pages/loan/book_loan.xhtml");
        setTitlePane("Book Loan - Template");
    }

    public void loan_schedule() {

        setPage("pages/loan/loan_schedule.xhtml");
        setTitlePane("Loan Schedule - Template");

    }

    public void approve_loan() {

        setPage("pages/loan/approve_loan.xhtml");
        setTitlePane("Approve Loan  - Template");

    }

    public void led_led_trnsfer() {

        setPage("pages/transact/ledger_to_ledger.xhtml");
        setTitlePane("Transfer Ledger To Ledger  - Template");

    }

    public void credit_account() {

        setPage("pages/transact/credit_transaction.xhtml");
        setTitlePane("Credit Account  - Template");

    }

    public void debit_account() {

        setPage("pages/transact/debit_transaction.xhtml");
        setTitlePane("Debit Account  - Template");

    }

    public void aprv_led_led_trnsfer() {

        setPage("pages/transact/apprv_ledger_to_ledger.xhtml");
        setTitlePane("Transfer Ledger To Ledger  - Template");

    }

    public void cus_mandate() {

        setPage("pages/customer/customer_account_mandate.xhtml");
        setTitlePane("CUSTOMER ACCOUNT MANDATE - Template");

    }

    public void act_to_act_trnx() {

        setPage("pages/transact/acct_to_acct_transaction.xhtml");
        setTitlePane("ACCOUNT TO ACCOUNT TRANSACTION - Template");

    }

    //
    public void account_sub_category() {

        setPage("pages/configacct/account_sub_category.xhtml");
        setTitlePane("Sub Account Category Configuration - Template");

    }

    public void staffEdit() {

        setPage("/staffs/updatestaff.xhtml");
        setTitlePane("Edit Staff Details");

    }

    public void roleUser() {
        setPage("/Pages/usermgt/role_control.xhtml");
        setTitlePane("Create User");
    }

    /**
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return the titlePane
     */
    public String getTitlePane() {
        return titlePane;
    }

    /**
     * @param titlePane the titlePane to set
     */
    public void setTitlePane(String titlePane) {
        this.titlePane = titlePane;
    }

}
