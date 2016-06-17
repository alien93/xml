package util;

/**
 * 
 * @author Milan
 *
 */
public class SaveDocumentHtmlPdf {
	
	private String collection;
	private String docId;
	
	/**
	 * 
	 * @param coll - kolekcija u kojoj je sacuvan dokument, npr : "/propisi/akti/doneti"
	 * @param id - oznaka dokumenta
	 */
	public SaveDocumentHtmlPdf(String coll, String id){
		collection = coll;
		docId = id;
	}
	
	public void save(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					new ZipHelper(collection, docId).transform();
				}catch(Exception e){}
			}
		}).start();
	}
	
	public void delete(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					new ZipHelper(collection, docId).delete();
				}catch(Exception e){}
			}
		}).start();
	}
}
