package ex03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//每次对数据进行了处理，进行一次输出，检查数据遗漏
//在解决线程池 的依赖性问题还不够完善

public class Main {
	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date date1 = new Date();
		System.out.println("Start ReadFile:00:00:00.000");
		ThreadPoolExecutor pool = (ThreadPoolExecutor)Executors.newFixedThreadPool(4);
		File file = new File("." + File.separator + "log") ;
		List<ReadFile> list = new ArrayList<ReadFile>() ;
		for(File f:file.listFiles()) {
			ReadFile rf = new ReadFile(f.getAbsolutePath()) ;
			list.add(rf) ;
			pool.execute(rf);
		}
		while(pool.getActiveCount()!=0 && pool.getQueue().size() != 0){
			;
		}
		System.out.println("finish reading files ");
		Date date2 = new Date() ;
		long nano = date2.getTime() - date1.getTime() ;
		long hour = nano/1000/60/60 ;
		long min = nano/1000/60 - hour*60 ;
		long sec = nano/1000 - min*60 - hour*60*60 ;
		long mill = nano - sec*1000 - min*1000*60 - hour*1000*60*60 ;
		System.out.println("End ReadFile:"  
				+ hour + ":" + min + ":" + sec + "." + mill);

		//操作
		Map<String , CarCtrl > map = new HashMap<String , CarCtrl>() ;
		List<ReadFileCtrl> carList = new ArrayList<>() ;
		//将所有车辆信息汇总
		for(ReadFile rf:list) {
			//车辆进出扫描
			ReadFileCtrl rfc = new ReadFileCtrl(rf) ;
			carList.add(rfc) ;
			pool.execute(rfc);
		}		
		while(pool.getActiveCount() != 0 && pool.getQueue().size() != 0) {
			;
		}
		for(ReadFileCtrl rfc:carList) {
			Map<String , CarCtrl> m = rfc.getReadFile().getMap() ;
			for(String str:m.keySet()) {
				if(!map.containsKey(str)) {
					map.put(str, m.get(str)) ;
				}else {
					map.get(str).addCars(m.get(str).getCars());
				}
			}
		}
		for(String str:map.keySet()) {
			pool.execute(map.get(str));
		}
		while(pool.getActiveCount() != 0 && pool.getQueue().size() != 0) {
			;
		}
		//
		pool.shutdown();
		
		Map<String , Integer > payMap = new HashMap<String , Integer>() ;
		for(String key:map.keySet()) {
			Map<String , Integer> carMap = map.get(key).getMap() ;
			for(String str:carMap.keySet()) {
				if(!payMap.containsKey(str)) {
					payMap.put(str, 0) ;
				}
				payMap.put(str, payMap.get(str) + carMap.get(str) ) ;
			}
		}
		
		for(String str:payMap.keySet()) {
			System.out.println(str + ":"+payMap.get(str) + "元") ;
		}
		
		
		Date date3 = new Date() ;
		nano = date3.getTime() - date1.getTime() ;
		hour = nano/1000/60/60 ;
		min = nano/1000/60 - hour*60 ;
		sec = nano/1000 - min*60 - hour*60*60 ;
		System.out.println("End Process:"  
				+ hour + ":" + min + ":" + sec + "." + mill);
	}
	
	
		
}

class ReadFile implements Runnable{
	private String path ;
	private boolean finish ;
	private List<Car> list ;
	private Map<String , CarCtrl> map ;
	
	public ReadFile() {}
	public ReadFile(String path) {
		this.path = path ;
		this.finish = false ;
		this.list = new ArrayList<Car>() ;
	}
	
	public boolean isFinish() {
		return this.finish ;
	}
	public List<Car> getList(){
		return this.list ;
	}
	//run只负责读取文件
	@Override
	public void run() {
		// TODO Auto-generated method stub
		FileReader file = null;
		try {
			file = new FileReader(this.path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(file);
		String str = "" ;
		try {
			while( str != null) {
				str = br.readLine() ;
				
				if(str != null ) {
					String[] s = str.split(",") ; 
					String s1 = s[1] ;
					if(!s1.equals("201725010120")) {
						continue ; 
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = null;
					try {
						date = sdf.parse(s[0]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					boolean in_or_out = (s[3].equals("in") ) ;//in为true ， out 为FALSE
					Car car = new Car(date , s[1] , s[2] , in_or_out);
					list.add(car) ;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//信息处理
	public void dataPreSet() {
		//按车牌放到字典中，车牌对应某辆车的进出记录
		this.map = new HashMap<String , CarCtrl>() ;
		for(Car car:this.list) {
			String str = car.getCarNum() ;
			if(!map.containsKey(str)) {
				map.put(car.getCarNum(), new CarCtrl(str)) ;
				map.get(str).addCar(car);
			}else {
				map.get(str).addCar(car) ;
			}
		}
	}
	
	public Map getMap() {
		return this.map ;
	}
}

class ReadFileCtrl implements Runnable{//实际为一个行为类，为了多线程给rf进行扫描操作
	private ReadFile rf ;
	
	public ReadFileCtrl(ReadFile rf) {
		this.rf = rf ;
	}
	public ReadFile getReadFile() {
		return this.rf ;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.rf.dataPreSet();
	}
}

class CarCtrl implements Runnable{//某辆车的停车日志
	private String carNum ;
	private List<Car> list ;
	private Map<String , Integer > payMoney ;//每辆车每个月给的钱
	private Map<String , Date> carIn ;
	private long[] stayTime ;
	public CarCtrl(String carNum) {
		this.carNum = carNum ;
		this.list = new ArrayList<Car>() ;
	}
	
	
	public void addCar(Car car) {
		this.list.add(car) ;
	}
	public void addCars(List<Car> cars) {
		this.list.addAll(cars) ;
	}
	public List<Car> getCars(){
		return this.list ;
	}
	public Map<String , Integer> getMap(){
		return this.payMoney ;
	}
	public long[] getStayTimes() {
		return this.stayTime ;
	}
	
	public int judgeTime(long time) {
		long minutes = time/1000/60 ;
		int pay = 0 ;
		if(minutes < 30) {
			;
		}else if( minutes < 120 ) {
			pay += 10 ;
		}else {
			pay += 10 ;
			minutes -= 120 ;
			while(minutes > 0) {
				pay += 2 ;
				minutes -= 60 ;
			}
		}
		return pay ;
	}
	
	@Override
	public void run() {//对某辆车的进出记录进行处理扫描统计
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM" ) ;
		//this.stayTime = new long[this.list.size()/2] ;
		this.payMoney = new HashMap<String , Integer>() ;
		this.carIn = new HashMap<String , Date >() ;
		long nano = 0 ;
		int count = 0 ;
		//有没有out先得?
		for(Car car:this.list) {
			if(car.isState()) {
				carIn.put(car.getCarNum(), car.getDate()) ;
			}else {
				String str = sdf.format(car.getDate());
				if(!this.payMoney.containsKey(str)) {
					this.payMoney.put(str , 0) ;
				}
				this.payMoney.put(str, this.payMoney.get(str) + this.judgeTime(car.getDate().getTime() - carIn.get(car.getCarNum()).getTime()));
			}
			
		}
	}
	
}

class Car{//一条车辆日志
	private Date date ;
	private String parkingNum ;
	private String carNum ;
	private boolean state ;
	
	public Car() {}
	
	public Car(Date date, String parkingNum, String carNum, boolean state) {
		super();
		this.date = date;
		this.parkingNum = parkingNum;
		this.carNum = carNum;
		this.state = state;
	}

	
	/////////访问器和修改器
	public Date getDate() {
		return date;
	}	

	public String getParkingNum() {
		return parkingNum;
	}

	public String getCarNum() {
		return carNum;
	}

	public boolean isState() {
		return state;
	}

}

class CarCompare implements Comparator<Car>{//用车牌排序？用日期排序？

	@Override
	public int compare(Car arg0, Car arg1) {
		// TODO Auto-generated method stub
		int i = arg0.getDate().compareTo(arg1.getDate()) ;
		if(i != 0) {
			return i ;
		}else {
			if(arg0.isState()) {
				return -1 ;
			}else {
				return 1 ;
			}
		}
	}
	
}

