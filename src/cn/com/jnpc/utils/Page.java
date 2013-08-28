package cn.com.jnpc.utils;
import java.util.List;

/**
 * @className:Page.java
 * @classDescription:分页参数对象
 */
public class Page<T> {
	
	//-- 分页参数 --//
	protected int pageNo = 1;// 页数
	protected int pageSize = 1;// 显示条数
	protected int leftCount=5;// 左边显示的条数
	protected int rigthCount=5;// 右边显示的条数
	private String forwordName;// 跳转页面
	protected List<T> result;//取得页内的记录列表.

	//-- 返回结果 --//
	protected long totalCount = -1;// 总条数


	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	//-- 访问查询参数函数 --//
	/**
	 * 获得当前页的页号,序号如果大于总条数，则当前页定位到总页数
	 */
	public int getPageNo() {
		if(this.getTotalPages()>-1&&this.pageNo>this.getTotalPages()){		
			this.pageNo=(int) this.getTotalPages();
		}
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
		
	}

	public Page<T> pageNo(final int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	public Page<T> pageSize(final int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}


	//-- 访问查询结果函数 --//

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 设置页内的记录列表.
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数.
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * @return the leftCount
	 */
	public int getLeftCount() {
		return leftCount;
	}

	/**
	 * @param leftCount the leftCount to set
	 */
	public void setLeftCount(int leftCount) {
		this.leftCount = leftCount;
	}

	/**
	 * @return the rigthCount
	 */
	public int getRigthCount() {
		return rigthCount;
	}

	/**
	 * @param rigthCount the rigthCount to set
	 */
	public void setRigthCount(int rigthCount) {
		this.rigthCount = rigthCount;
	}

	/**
	 * @return the forwordName
	 */
	public String getForwordName() {
		return forwordName;
	}

	/**
	 * @param forwordName the forwordName to set
	 */
	public void setForwordName(String forwordName) {
		this.forwordName = forwordName;
	}
	
	/**
	 * 获取分页显示标签
	 * 
	 * @param page
	 *            page对象
	 * @return
	 */
	public  String getTag() {
	    StringBuffer tag=new StringBuffer();
	    try{
		int pageCount = leftCount+rigthCount;
		long totalPages = getTotalPages();
		tag.append("共  "	+ totalPages + " 页  " );
		if(isHasPre()){
            tag.append("  <a href='" + getForwordName() +
					+ 1 + "'>首页</a>&nbsp;");
            tag.append("  <a href='" + getForwordName()
					+ getPrePage() + "'>上页</a>&nbsp;");
		}else{
			tag.append("  首页&nbsp;");
			tag.append("  上页&nbsp;");
		}

		if(totalPages >= pageCount){
			if(pageNo < pageCount){
				for(int i=1; i <= pageCount; i ++){
					setPageTagNum(tag, i);
				}
			}else if(totalPages - pageNo < pageCount){
				for(long i= totalPages-pageCount+1; i <= totalPages; i ++){
					if(i == pageNo){
						tag.append("  "+i+"&nbsp;");
					}else{
                        tag.append("  <a href='" + getForwordName() +
								+ i + "'>"+i+"</a>&nbsp;");
					}
				}
			}else{
				for(int i=pageNo; i <= pageCount+pageNo; i ++){
					setPageTagNum(tag, i);
				}
			}
		}else{
			if(totalPages > 1){
				for(int i =1; i <= totalPages; i ++){
					setPageTagNum(tag, i);
				}
			}
		}
		
		if(isHasNext()){
            tag.append("  <a href='" + getForwordName()
					+ getNextPage() + "'>下页</a>&nbsp;");
            tag.append("  <a href='" + getForwordName()
					+ getTotalPages() + "'>尾页</a>&nbsp;");
		}else{
			tag.append("  下页&nbsp;");
			tag.append("  尾页&nbsp;");
			
		}

//		tag.append("共" + page.getTotalCount() + "条  每页" + page.getPageSize() + "条  总共"
//				+ page.getTotalPages() + "页 " + page.getPageNo() + "/" + page.getTotalPages() + "  ");
		
		return tag.toString();
	    }catch(Exception e){
		e.getMessage();
		e.getCause();
	    }finally{
		return tag.toString();
	    }
	}

	private void setPageTagNum(StringBuffer tag, int i) {
		if(i == pageNo){
			tag.append("  "+i+"&nbsp;");
		}else{
			tag.append("  <a href='" + getForwordName()+
					+ i + "'>"+i+"</a>&nbsp;");
		}
	}
}
