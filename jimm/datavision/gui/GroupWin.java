package jimm.datavision.gui;
import jimm.datavision.*;
import jimm.datavision.gui.cmd.GroupEditCommand;
import jimm.datavision.source.Column;
import jimm.util.I18N;
import java.awt.event.*;
import java.util.*;

/**
 * This dialog is used for editing report groups.
 *
 * @author Jim Menard, <a href="mailto:jim@jimmenard.com">jim@jimmenard.com</a>
 */
@SuppressWarnings("serial")
public class GroupWin extends TwoListWin {

/**
 * Constructor.
 *
 * @param designer the window to which this dialog belongs
 * @param report the...um...I forgot
 */
public GroupWin(Designer designer, Report report) {
    super(designer, I18N.get("GroupWin.title"), "GroupChangeCommand.name",
	  "GroupWin.right_box_title", report);
}

protected void fillListModels() {
    // First find and add groups in order.
    for (Group group : report.groups())
	rightModel.addElement(new GroupWinListItem(group.getSelectable(),
						   group));

    // Now iterate through all user cols and columns in tables used by the
    // report, adding to the left list those that are not already grouped
    // to the left list.
    for (UserColumn uc : report.userColumns())
	addToModel(uc);
    for (Column col : report.getDataSource().columnsInTablesUsedInReport())
	addToModel(col);
}

protected void addToModel(Selectable s) {
    Group group = report.findGroup(s);
    if (group == null)
	leftModel.add(new GroupWinListItem(s, group));
}

/**
 * Handles ascending and descending sort order buttons.
 */
public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    if (cmd.equals(I18N.get("GUI.ascending")))
	((GroupWinListItem)rightList.getSelectedValue()).sortOrder =
	    Group.SORT_ASCENDING;
    else if (cmd.equals(I18N.get("GUI.descending")))
	((GroupWinListItem)rightList.getSelectedValue()).sortOrder =
	    Group.SORT_DESCENDING;
    else
	super.actionPerformed(e);
}

@SuppressWarnings("unchecked")
protected void doSave() {
    // Turn the model's vector into a collection
    ArrayList<GroupWinListItem> items = new ArrayList<GroupWinListItem>();
    for (Enumeration<GroupWinListItem> e = (Enumeration<GroupWinListItem>)rightModel.elements(); e.hasMoreElements(); )
	items.add((GroupWinListItem)e.nextElement());

    GroupEditCommand cmd = new GroupEditCommand(report, designer, items);
    cmd.perform();
    commands.add(cmd);
}

protected void doRevert() {
    // Rebuild list models
    leftModel.removeAllElements();
    rightModel.removeAllElements();
    fillListModels();
    adjustButtons();
}

}
