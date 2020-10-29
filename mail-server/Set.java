package mailServer;

public class Set {
	IFolder folder=new Folder();
	IFilter filter = new Filter();
	ISort sort=new Sort();
	IMail mail = new Mail();
	IContact contact = new Contact();
	public void setFolder(IFolder f) {
		folder = f;
	}
	public void setFilter(IFilter f) {
		filter = f;
	}
	public void setSort(ISort f) {
		sort = f;
	}
	public void setMail(IMail f) {
		mail = f;
	}
	public void setContact(IContact f) {
		contact = f;
	}
	public IFolder getFolder() {
		return folder ;
	}
	public IFilter getFilter() {
		return filter;
	}
	public ISort getSort() {
		return sort;
	}
	public IMail getMail() {
		return mail;
	}
	public IContact getContact() {
		return contact;
	}
	
}
