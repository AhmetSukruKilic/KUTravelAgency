package guiManagment;


public final class PreviousNextDataBase extends PageDataBase{
	
	private PreviousNextDataBase() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}
	
	public static void previousPage() {
		if (currentPage != null) {
			nexts.add(currentPage);
			currentPage.dispose();
		}
		cleanPanel();
		currentPage = findTheClassPage(previouses.pop());
		currentPage.setVisible(true);
	}
 
	public static void nextPage() {
		if (currentPage != null) {
			previouses.add(currentPage);
			currentPage.dispose();
		}
		cleanPanel();
		currentPage = findTheClassPage(nexts.pop());
		currentPage.setVisible(true);
	}
	
	
	private static Page findTheClassPage(Page oldPage) {
	    return oldPage.createPage(sharedPanel);
	}

}
