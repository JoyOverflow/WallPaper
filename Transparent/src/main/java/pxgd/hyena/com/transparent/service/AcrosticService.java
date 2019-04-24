package pxgd.hyena.com.transparent.service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 抓取数据
 */
public class AcrosticService {
	@SuppressWarnings("unused")
	private static final String TAG = AcrosticService.class.getSimpleName();
	private static final String CTS_388G_URL = "http://cts.388g.com/fasong.php";
	
	@SuppressWarnings("serial")
	public Map<String, String> numMap = new LinkedHashMap<String, String>() {
		{
			put("五言", "5");
			put("七言", "7");
		}
	};
	@SuppressWarnings("serial")
	public Map<String, String> typeMap = new LinkedHashMap<String, String>() {
		{
			put("藏头", "1");
			put("藏尾", "2");
			put("藏中", "3");
			put("递增", "4");
			put("递减", "5");
		}
	};
	@SuppressWarnings("serial")
	public Map<String, String> yayuntypeMap = new LinkedHashMap<String, String>() {
		{
			put("双句一压", "1");
			put("双句押韵", "2");
			put("一三四押", "3");
		}
	};
	
	public String getAcrosticInfo(String words, String num, String type, String yayuntype) {
		if (words == null || words.equals("") || num == null || num.equals("") || type == null || type.equals("")
				|| yayuntype == null || yayuntype.equals("")) {
			return null;
		}
		String url = CTS_388G_URL+"?w="+HttpUtils.encodeURI(words, "gbk")+"&num="+numMap.get(num)+"&type="+typeMap.get(type)+"&yayuntype="+yayuntypeMap.get(yayuntype);
		String html = HttpUtils.doGetFor388g(url, "gbk");
		return html;
	}
}
