package bzb.util;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// TODO Auto-generated method stub
		CollectionRecordThread collectionRecord = new CollectionRecordThread();
		Thread thread = new Thread(collectionRecord, "collection_sign_record");
		thread.start();
		
		
		//初始化用户缓存
		String sql = "select * from user where enabled = 1 ";
		List<Map<String, Object>> list = DBUtil.executerQuery(sql);
		for(Map<String, Object> map: list){
			Cache.user.put("" + map.get("id"), map);
		}		
	}

}
