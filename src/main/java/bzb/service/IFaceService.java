package bzb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IFaceService {

	/**
	 * 连接机器
	 * 
	 * @param ipaddr
	 *            IP地址
	 * @param port
	 *            端口，默认4370
	 * @return true or false
	 */
	public static native boolean Connect_net(String ipaddr, long port);

	/**
	 * 断开机器连接，释放相关资源
	 */
	public static native void Disconnect();

	/**
	 * 读取考勤记录到 PC 的内部缓冲区，同 ReadAllGLogData
	 * 
	 * @param dwMachineNumber
	 *            机器号
	 * @return 成功返回 True，否则返回 False
	 */
	public static native boolean ReadGeneralLogData(long dwMachineNumber);

	/**
	 * 读取考勤记录到 PC 的内部缓冲区，同 ReadGeneralLogData
	 * 
	 * @param dwMachineNumber
	 *            机器号
	 * @return 成功返回 True，否则返回 False
	 */
	public static native boolean ReadAllGLogData(long dwMachineNumber);

	/**
	 * 清除机器内所有考勤记录
	 * 
	 * @param dwMachineNumber
	 *            机器号
	 * @return 成功返回 True,否则返回 False
	 */
	public static native boolean ClearGLog(long dwMachineNumber);

	/**
	 * 读取所有的用户信息到 PC 内存中，包括用户编号，密码，姓名，卡号等，指纹模板除外。在该函数执行完 成后，可调用函数 GetAllUserID
	 * 取出用户信息
	 * 
	 * @param dwMachineNumber
	 *            机器号
	 * @return 成功返回 True，否则返回 False
	 */
	public static native boolean ReadAllUserID(long dwMachineNumber);

	/**
	 * 设置用户是否可用
	 * 
	 * @param dwMachineNumber
	 *            机器号
	 * @param dwEnrollNumber
	 *            用户号
	 * @param bFlag
	 *            用户启用标志，True 为启用，False 为禁用
	 * @return 成功返回 True，否则返回 False
	 */
	public static native boolean SSR_EnableUser(long dwMachineNumber, int dwEnrollNumber, boolean bFlag);

	private static native boolean SSR_GetAllUserInfo(long dwMachineNumber, int dwEnrollNumber, String Name,
			String Password, long Privilege, boolean Enabled);

	/**
	 * 取得所有用户信息
	 * 
	 * @param dwMachineNumber
	 *            机器号
	 * @return
	 */
	public static List<Map<String, Object>> getAllUserInfo(long dwMachineNumber) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		ReadAllUserID(dwMachineNumber);
		int dwEnrollNumber = -1;
		String Name = null;
		String Password = null;
		long Privilege = -1;
		boolean Enabled = false;
		while (SSR_GetAllUserInfo(dwMachineNumber, dwEnrollNumber, Name, Password, Privilege, Enabled)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("machinenumber", dwMachineNumber);
			map.put("enrollnumber", dwEnrollNumber);
			map.put("name", Name);
			map.put("password", Password);
			map.put("privilege", Privilege);
			map.put("enabled", Enabled);
			result.add(map);
		}
		return result;
	}

	/**
	 * 设置指定用户的用户信息, 若机器内没用该用户，则会创建该用户
	 * 
	 * @param dwMachineNumber
	 *            机器号
	 * @param dwEnrollNumber
	 *            用户号
	 * @param Name
	 *            用户姓名
	 * @param Password
	 *            用户密码
	 * @param Privilege
	 *            用户权限，3 为管理员，0 为普通用户
	 * @param Enabled
	 *            1 为启用，0 为禁用
	 * @return
	 */
	private static native boolean SSR_SetUserInfo(long dwMachineNumber, int dwEnrollNumber, String Name,
			String Password, long Privilege, boolean Enabled);

	/**
	 * 设置指定用户的用户信息, 若机器内没用该用户，则会创建该用户
	 * 
	 * @param dwMachineNumber
	 *            机器号
	 * @param dwEnrollNumber
	 *            用户号 新建时，请指定为-1
	 * @param Name
	 *            用户姓名
	 * @param Password
	 *            用户密码
	 * @param Privilege
	 *            用户权限，3 为管理员，0 为普通用户
	 * @param Enabled
	 *            1 为启用，0 为禁用
	 * @return
	 */
	public static boolean addOrUpdateUserInfo(long dwMachineNumber, int dwEnrollNumber, String Name, String Password,
			long Privilege, boolean Enabled) {
		boolean result = false;
		if (dwEnrollNumber == -1) {
			int max = 0;
			List<Map<String, Object>> list = getAllUserInfo(dwMachineNumber);
			for (Map<String, Object> map : list) {
				int num = Integer.parseInt(map.get("enrollnumber").toString());
				max = max > num ? max : num;
			}
			dwEnrollNumber = max + 1;
		}
		result = SSR_SetUserInfo(dwMachineNumber, dwEnrollNumber, Name, Password, Privilege, Enabled);
		return result;
	}

	/**
	 * 
	 * @param dwMachineNumber
	 * @param dwEnrollNumber
	 * @param dwBackupNumber
	 *            指纹索引 一般范围为 0-9，同时会查询该用户是否还有其他指纹和密码，如都没有，则删除该用户 当为 10
	 *            是代表删除的是密码，同时会查询该用户是否有指纹数据，如没有，则删除该用户 11 是代表删除该用户所有指纹数据，12
	 *            代表删除该用户（包括所有指纹和卡号、密码数据）
	 * @return
	 */
	private static native boolean SSR_DeleteEnrollDataExt(long dwMachineNumber, int dwEnrollNumber, long dwBackupNumber);

	/**
	 * 删除登记数据,包括所有指纹和卡号、密码数据
	 * 
	 * @param dwMachineNumber
	 * @param dwEnrollNumber
	 * @return
	 */
	public static boolean deleteEnrollData(long dwMachineNumber, int dwEnrollNumber) {
		boolean result = false;
		SSR_DeleteEnrollDataExt(dwMachineNumber, dwEnrollNumber, 13);
		return result;
	}
	/**
	 * 刷新机器内数据，一般在上传用户信息或指纹后调用，这样能使所作的修改立即起作用，起到同步作用。
	 * @param dwMachineNumber
	 * @return
	 */
	public static native boolean RefreshData(long dwMachineNumber);
	
	

}
