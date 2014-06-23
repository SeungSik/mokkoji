package com.mokkoji.app.json;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




import java.util.StringTokenizer;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mokkoji.app.checkgschedule.CheckGschedule;
import com.mokkoji.app.checkgschedule.CheckGscheduleDao;
import com.mokkoji.app.group.Group;
import com.mokkoji.app.group.GroupDao;
import com.mokkoji.app.group.Grouplist;
import com.mokkoji.app.group.NewGroup;
import com.mokkoji.app.groupmanagement.GroupManagement;
import com.mokkoji.app.groupmanagement.GroupManagementDao;
import com.mokkoji.app.gschedule.Gschedule;
import com.mokkoji.app.gschedule.GscheduleDao;
import com.mokkoji.app.gschedule.GscheduleList;
import com.mokkoji.app.gsmanagement.Gsmanagement;
import com.mokkoji.app.gsmanagement.GsmanagementDao;
import com.mokkoji.app.member.Member;
import com.mokkoji.app.member.MemberDao;
import com.mokkoji.app.member.Memberlist;
import com.mokkoji.app.recomschedule.Recomschedule;
import com.mokkoji.app.recomschedule.RecomscheduleDao;
import com.mokkoji.app.recomschedule.RecomscheduleList;
import com.mokkoji.app.schedule.Schedule;
import com.mokkoji.app.schedule.ScheduleDao;
import com.mokkoji.app.schedule.ScheduleList;
import com.mokkoji.app.usergschedule.Usergschedule;
import com.mokkoji.app.usergschedule.UsergscheduleDao;


@Controller
public class JsonController {

	@Inject
	private MemberDao memberDao;
	@Inject
	private GroupDao groupDao;
	@Inject
	private GroupManagementDao groupmanagementdao;
	@Inject
	private ScheduleDao scheduledao;
	@Inject
	private GscheduleDao gscheduledao;
	@Inject
	private UsergscheduleDao usergscheduledao;
	@Inject
	private GsmanagementDao gsmanagementdao;
	@Inject
	private CheckGscheduleDao checkgscheduledao;
	@Inject
	private RecomscheduleDao recomscheduledao;
	
	
	
	private static Logger logger = LoggerFactory
			.getLogger(JsonController.class);

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public String getTest() {
		return "json";
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String jsonTest(@ModelAttribute Member membmer) {
		logger.info(membmer.toString());
		return "redirect:/json";
	}

	@ResponseBody
	@RequestMapping(value = "/postJson.json", method = RequestMethod.POST)
	public Member postTest(@RequestBody Member member) {
		logger.info(member.toString());
		System.out.println("test");

		Member getMember = memberDao.getMemberFromId(member.getId());
		if(getMember != null){    //아이디가 존재하는 경우 
			
			System.out.println(getMember.getPassword());
			System.out.println(member.getPassword());
			
			if (member.getPassword().equals(getMember.getPassword())) {

				return new Member(getMember.getPn(), getMember.getId(),
						getMember.getPassword(), getMember.getEmail());
			} else
				return new Member(getMember.getPn(), null, getMember.getPassword(),
						getMember.getEmail());
	
		}else{  //존재 하지 않는 경우 
			
			return new Member(null, null, null,null);
			
		}

	}
	@ResponseBody
	@RequestMapping(value = "/IdCheckJson.json", method = RequestMethod.POST)
	public Integer IdCheckTask(@RequestBody String memberid) {
		String id = new String();
		Member temp = new Member();
		id = memberid;
		Integer message;
		temp = memberDao.getMemberFromId(id);
		if(memberid == null){
			message =1;
			
		}else if(temp != null){
			message =2;
		}else {
			message =3;
		}
		return message;
	}
	
	@ResponseBody
	@RequestMapping(value = "/JoinJson.json", method = RequestMethod.POST)
	public Member JoinTest(@RequestBody Member member) {
		logger.info(member.toString());
		System.out.println("join");
		Member temp = new Member();
		Member newuser = new Member();
		newuser.setId(member.getId());
		
		temp = memberDao.getMemberFromId(newuser.getId());
		
		if(temp == null){
			memberDao.insertMember(member);
		}else{
			member.setId(null);
		}

		return member;
	}

	@ResponseBody
	@RequestMapping(value = "/getJson.json", method = RequestMethod.GET)
	public Member getTest(@ModelAttribute Member member) {
		logger.info(member.toString());
		return new Member(2, "Jung1234", "1q2w3e4r!", "qwer@naver.com");
	}

	@ResponseBody
	@RequestMapping(value = "/postlistJson.json", method = RequestMethod.POST)
	public Memberlist postlistTest(@RequestBody Memberlist member) {
		logger.info(member.toString());

		Memberlist mems = new Memberlist();

		List<Member> memberlist = memberDao.getMemeberList();
		mems.setMemberlist((ArrayList<Member>) memberlist);
		member = mems;

		return member;
	}

	@ResponseBody
	@RequestMapping(value = "/PostRecomschedule.json", method = RequestMethod.POST)
	public Recomschedule PostRecomschedule(@RequestBody Recomschedule recom) {
		if(recom != null)	{
			recomscheduledao.insertRecomschedule(recom);
			return recom;
		}else{
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/PostGetRecomschedule.json", method = RequestMethod.POST)
	public RecomscheduleList PostGetRecomschedule(@RequestBody Recomschedule recom) {
		
		
		RecomscheduleList newscheduleList= new RecomscheduleList();
		
		System.out.println(""+recom.getUserpn());
		List<Recomschedule> recomscheduleList = recomscheduledao.getRecomSchedules(recom.getUserpn());
		
		newscheduleList.setScheduleList((ArrayList<Recomschedule>)recomscheduleList);
			
		System.out.println(""+newscheduleList.getSchedulelist().size());
			return newscheduleList;		
			
		
	}

	@ResponseBody
	@RequestMapping(value = "/postgroupJson.json", method = RequestMethod.POST)
	public NewGroup postgroupCreate(@RequestBody NewGroup newgroup) {
		
		Group group = new Group();
		group.setGn(newgroup.getGroup().getGn());
		group.setGname(newgroup.getGroup().getGname());
		group.setMasterPn(newgroup.getGroup().getMasterPn());
		
		logger.info(group.toString());
		System.out.println();
		System.out.println("create");
		
		
		groupDao.insertGroup(group);
		
		int group_number = groupDao.getGroupList().size();
		
		Memberlist mems = newgroup.getMemberlist();
		int size = mems.getMemberlist().size();
		for(int i=0; i<size; i++){
			GroupManagement gm = new GroupManagement();
			gm.setGroup_gn(group_number);
			gm.setUser_pn(mems.getMemberlist().get(i).getPn());
			groupmanagementdao.insertGroupManagement(gm);
		}
		return newgroup;
	}

	//memOfgroupList
	@ResponseBody
	@RequestMapping(value = "/memOfgroupList.json", method = RequestMethod.POST)
	public Memberlist memOfgroupList(@RequestBody Integer gn) {
	
		Memberlist friends = new Memberlist();
		ArrayList<Member> mygroup = new ArrayList<Member>();
		
		//그룹에 해당되는 member들 가져옴
		List<GroupManagement> gmlist  = groupmanagementdao.getGroupManagementFromgroup_gn(gn);
	
		int size = gmlist.size();
		for(int i=0; i<size; i++){
			Integer pn = gmlist.get(i).getUser_pn();
			mygroup.add(memberDao.getMemberFromPn(pn));
		}
		
		friends.setMemberlist(mygroup);
		return friends;
	}
	
	@ResponseBody
	@RequestMapping(value = "/postgrouplistJson", method = RequestMethod.POST)
	public Grouplist postgrouplistTest(@RequestBody Integer user_pn) {
	
		Grouplist groups = new Grouplist();
		ArrayList<Group> mygroup = new ArrayList<Group>();
		ArrayList<Integer> groupgn = new ArrayList<Integer>();
		
		List<GroupManagement> gmlist = groupmanagementdao.getGroupManagementList();
		int size = gmlist.size();
		for(int i=0; i<size; i++){
			if(gmlist.get(i).getUser_pn() == user_pn){    //사용자의 pn을 management테이블에서 검색해서 gn가져옴
				groupgn.add(gmlist.get(i).getGroup_gn());   //사용자가 있는 gn가져옴
			}
		}
		int size_group = groupgn.size();
		for(int i=0; i<size_group; i++){  //gn에 해당되는 그룹들을 가져옴 
		
			mygroup.add(groupDao.getGroupFromgn(groupgn.get(i)));
		}
		groups.setGrouplist(mygroup);
		return groups;
	}
	@ResponseBody
	@RequestMapping(value = "/ModifySchedule.json", method = RequestMethod.POST)
	public void ModifySchedule(@RequestBody Schedule schedule) {
		logger.info(schedule.getSn().toString());
		scheduledao.updateSchedule(schedule);
		
	}
	@ResponseBody
	@RequestMapping(value = "/ModifyGschedule.json", method = RequestMethod.POST)
	public Integer ModifyGschedule(@RequestBody Gschedule gschedule) {
		Gschedule temp = new Gschedule();
		temp = gscheduledao.getGschedule(gschedule.getSn());
		if(temp.getUser_pn() == gschedule.getUser_pn()){ //처음 일정 만든 사람이랑 같은 경우에만 update
	
			gscheduledao.updateGschedule(gschedule);
			return 1;
			
		}else{
			return 0;
			
		}

	}
	@ResponseBody
	@RequestMapping(value = "/postScheduleToSql.json", method = RequestMethod.POST)
	public void postTest(@RequestBody Schedule schedule) {
		logger.info(schedule.toString());

		System.out.println(schedule.getStartDate());
		System.out.println(schedule.getUserPn().toString());
		scheduledao.insertSchedule(schedule);

	}
	@ResponseBody
	@RequestMapping(value = "/postGscheduleToSql.json", method = RequestMethod.POST)  //그룹 스케줄 등록 
	public void postTest(@RequestBody Gschedule gschedule) throws InterruptedException {
		logger.info(gschedule.toString());
		
		int gn , userpn;
		gn = gschedule.getGn();
		userpn = gschedule.getUser_pn();
		
		System.out.println(gschedule.getStartDate());
		System.out.println(gschedule.getUser_pn().toString());
		gscheduledao.insertGschedule(gschedule);
		
		Integer sn = gscheduledao.selectlastsn();
		System.out.println("!!!!!!!!!!!!!"+sn+"!!!!!!!!");
		Thread.sleep(300);
		Gsmanagement gsmanagement = new Gsmanagement();
		gsmanagement.setGroup_gn(gn);
		gsmanagement.setGroup_sn(sn);
		gsmanagementdao.insertGsmanagement(gsmanagement);
		//그리고  group에 포함된 사람들에게 메세지 넘겨주기 위해 checkschedule을 해당된 groupmember들에게 보여줌! 
		//check이거는 새로운 일정등록에 대한 확인을 했을 때만 적용할 것임!!! -> 누군가 수락을 했을 때
		//userschedule로 등록해줄 것!
		
		List<GroupManagement> gm = groupmanagementdao.getGroupManagementFromgroup_gn(gschedule.getGn());
		int gm_size = gm.size();
		for(int i=0; i<gm_size; i++){   //group에 포함된 user들 pn가져옴 
			CheckGschedule check = new CheckGschedule();
			Integer upn = gm.get(i).getUser_pn();     //group에속한 member들 가져오기 
			check.setChecked((Integer)0);      //처음 초기화는 0으로 
			check.setGroup_sn(sn);           //schedule num등록
			check.setUser_pn(upn);           //사용자들 등록 
			checkgscheduledao.insertCheckGschedule(check);   //해당 사항들을 기록해줌 
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "/PostListofSchedule.json", method = RequestMethod.POST)
	public ScheduleList PostListofSchedule(@RequestBody Integer userPn) {

	      ScheduleList schedulelist = new ScheduleList();
	      
	      
	      List<Schedule> scl = (List<Schedule>) scheduledao.getSchedules(userPn);//scl => scheduleList를 줄여쓴것 헷갈리지마
	      
	      schedulelist.setScheduleList((ArrayList<Schedule>)scl);
	      
	      System.out.println("일단 여기까지 잘 들어온다");

	      return schedulelist;
	 }
	@ResponseBody
	@RequestMapping(value = "/PostListofGSchedule.json", method = RequestMethod.POST)
	public GscheduleList PostListofGSchedule(@RequestBody Gschedule gschedule) {
	//gschedule은  그룹 스케줄을 받아오는데, group스케줄에 등록되어있으면서 사용자가 해당 그룹 스케줄을 선택했을 때만 받아온다.
	      
		  GscheduleList gschedulelist = new GscheduleList();
	      ArrayList<Gschedule> temp = new ArrayList<Gschedule>();
	      Integer gn = gschedule.getGn();
	      Integer userPn = gschedule.getUser_pn();
	      Usergschedule ug= new Usergschedule();
	      ug.setGs_gn(gn);
	      ug.setUser_pn(userPn);
	      List<Usergschedule> us = usergscheduledao.getUsergscheduleFromug(ug);
	      int us_size =us.size();
	      System.out.println(us_size);
	      for(int i=0; i<us_size; i++){
	      
	    	  Integer sn = us.get(i).getGs_sn();
	    	  temp.add(gscheduledao.getGschedule(sn));
	      }

		      gschedulelist.setGscheduleList(temp);
	    
	      return gschedulelist;
	 }
	@ResponseBody   //group gn 에 해당되는 전체 스케줄 리스트를 받아옴 
	@RequestMapping(value = "/wholegslistJson.json", method = RequestMethod.POST)
	public GscheduleList PostwholegslistJson(@RequestBody Gschedule gschedule) {

		 Integer groupgn = gschedule.getGn();
		 Integer userpn = gschedule.getUser_pn();
		 CheckGschedule cks = new CheckGschedule();
		 
		  GscheduleList gschedulelist = new GscheduleList();
		  
		  List<Gschedule> unckedlist = new ArrayList<Gschedule>();
		  List<Gsmanagement> temp = gsmanagementdao.getGsmanagementFromgroup_gn(groupgn);  //groupgn인 스케줄들 가져오기
		  
		  int temp_size = temp.size();
		  for(int i=0; i<temp_size; i++){   //그룹 넘버에 해당되는 그룹스케줄 넘버를 볼수 있음 (sn)
			  //sn과 groupgn을 알고, user pn을 아니까 false인거 검색함 
			  cks.setChecked(0);
			  cks.setGroup_sn(temp.get(i).getGroup_sn());
			  cks.setUser_pn(userpn); 
			  //확인안한 스케줄이 있다면 그것을 가져올것 
			  if(checkgscheduledao.getCheckScheduleFromCheck(cks) != null){
				  unckedlist.add(gscheduledao.getGschedule(temp.get(i).getGroup_sn()));
			  }
		  }
		  gschedulelist.setGscheduleList((ArrayList)unckedlist);  //확인 안한 스케줄 리턴 해줌! 
	      return gschedulelist;
	 }
	@ResponseBody   //group gn 에 해당되는 전체 스케줄 리스트를 받아옴 
	@RequestMapping(value = "/checkgscheduleJson.json", method = RequestMethod.POST)
	public CheckGschedule PostCheckedTask(@RequestBody CheckGschedule gs) {

		 Integer groupsn = gs.getGroup_sn();
		 Integer userpn = gs.getUser_pn();
		
		 //checked를 0->1로 바꿔줌 
		 CheckGschedule cks = new CheckGschedule();
		 cks.setChecked(1);
		 cks.setGroup_sn(groupsn);
		 cks.setUser_pn(userpn);

		 if(checkgscheduledao.getCheckScheduleFromCheck(gs) != null){
			checkgscheduledao.updateCheckGschedule(cks);
		
		 }
	
	      return cks;
	 }
	@ResponseBody  
	@RequestMapping(value = "/deleteSchedule.json", method = RequestMethod.POST)
	public Schedule deleteSchedule(@RequestBody Integer sn) {
		
		Schedule sc = new Schedule();
		sc = scheduledao.getSchedule(sn);
			
		
		if(scheduledao.getSchedule(sn)!= null){
			scheduledao.deleteSchedule(sn);
		}
		
	     return sc;
	 }
	@ResponseBody  
	@RequestMapping(value = "/deleteGschedule.json", method = RequestMethod.POST)
	public Gschedule deleteGschedule(@RequestBody Gschedule temp) {
		Integer sn = temp.getSn();
		Integer userPn = temp.getUser_pn();
		
		Gschedule sc = new Gschedule();
		sc = gscheduledao.getGschedule(sn);
		
		if(gscheduledao.getGschedule(sn)!= null){
			
			if(sc.getUser_pn() == userPn)
				gscheduledao.deleteGschedule(sn);
			else
				sc.setUser_pn(null);
		}
		
	     return sc;
	 }
	
	@ResponseBody  
	@RequestMapping(value = "/AcceptgscheduleJson.json", method = RequestMethod.POST)
	public Usergschedule PostAcceptGs(@RequestBody Usergschedule ug) {
		//등록해주는 것 -> user의 schedule에!! 성공 
		 Usergschedule temp = new Usergschedule();
		 Integer groupsn = ug.getGs_sn();
		 Integer groupgn = ug.getGs_gn();
		 Integer userpn = ug.getUser_pn();
		 temp.setGs_gn(groupgn); temp.setGs_sn(groupsn); temp.setUser_pn(userpn);

		 usergscheduledao.insertUsergschedule(temp);
	      return temp;
	 }
	@ResponseBody
	@RequestMapping(value = "/groupexitJson.json", method = RequestMethod.POST)
	public GroupManagement GroupExit(@RequestBody GroupManagement gm) {
	     
		GroupManagement groupmanage = new GroupManagement();
		groupmanage.setGroup_gn(gm.getGroup_gn());
		groupmanage.setUser_pn(gm.getUser_pn());
		groupmanagementdao.deleteGroupManagement(groupmanage);

	      return groupmanage;
	   }
	
	@ResponseBody
	   @RequestMapping(value = "/findkeyword.json", method = RequestMethod.POST)
	   public ScheduleList findkeyword(@RequestBody Schedule temp) {
	      
	   
	      System.out.println( temp.getContent());
	      
	      String keyword = ".*"+temp.getContent()+".*";
	      Integer userpn = temp.getUserPn();
	      
	      List<Schedule> schedules = scheduledao.getSchedules(userpn);
	      int schedule_size = schedules.size();
	      ScheduleList findlist = new ScheduleList();
	      
	      
	      for(int i=0; i<schedule_size; i++){
	         
	         if(schedules.get(i).getTitle().matches(keyword) == true || schedules.get(i).getContent().matches(keyword)== true){  //title에 keyword가 있는 경우
	            findlist.getSchedulelist().add(schedules.get(i));
	         }
	      }
	      
	      return findlist;
	      
	   }
	
	   @ResponseBody
	   @RequestMapping(value = "/findWeekScheudle.json", method = RequestMethod.POST)
	   public ScheduleList findWeekScheudle(@RequestBody Schedule schedule) {
		   
		   Integer userPn = schedule.getUserPn();
		      String theweek_first = schedule.getStartDate();   //그 주의 첫째 날짜 
		      StringTokenizer st1=new StringTokenizer(theweek_first ,"-");//가져온것을 -로 자른다
		      int getyear_first = Integer.parseInt(st1.nextToken());
		      int getmonth_first = Integer.parseInt(st1.nextToken());
		      int getday_first = Integer.parseInt(st1.nextToken());
		      
		      
		      System.out.println(""+getyear_first+" "+getmonth_first+" "+getday_first+"ssssss");
		      String prev_enddate =null; //이전 달의 마지막 날짜 받아옴
		      int getyear_end =0;
		      int getmonth_end=0;
		      int getday_end=0;
		      int cur_count = 7;
		      
		      //만약 12월달의 next month는 다음 년의 1월이니까 그걸 적용 
		      int next_year = getyear_end;
		      int next_month = getmonth_end;

		      if(schedule.getEndDate()!= null){
		         prev_enddate = schedule.getEndDate();
		         StringTokenizer st2=new StringTokenizer(theweek_first ,"-");//가져온것을 -로 자른다
		         getyear_end = Integer.parseInt(st2.nextToken());
		         getmonth_end = Integer.parseInt(st2.nextToken());
		         getday_end = Integer.parseInt(st2.nextToken());
		         cur_count =cur_count - getday_end - getday_first +1;
		         System.out.println(""+getyear_end+" "+getmonth_end+" "+getday_end+"ssssss");
		      }
		      
		      List<Schedule> listofschedule = scheduledao.getSchedules(userPn);   //userpn에 해당되는 스케줄 가져옴
		      ScheduleList result = new ScheduleList();
		      int list_size = listofschedule.size();
		      
		      for(int i=0; i<list_size; i++){   //날짜에 해당되는 일정만 가져옴 
		         
		         String startDate = listofschedule.get(i).getStartDate();
		         StringTokenizer st=new StringTokenizer(startDate,"-");//가져온것을 -로 자른다
		         int getyear = Integer.parseInt(st.nextToken());
		         int getmonth = Integer.parseInt(st.nextToken());
		         int getday = Integer.parseInt(st.nextToken());
		         
		         System.out.println(""+getyear+" "+getmonth+" "+getday);
		         
		         if(prev_enddate== null){    //그 주가 해당  달에 모두 있는 경우
		        	 System.out.println("첫번째");
		            if(getyear == getyear_first && getmonth == getmonth_first && getday>= getday_first&& getday<=getday_first+6){
		               result.getSchedulelist().add(listofschedule.get(i));
		               System.out.println("겟이어퍼스트");
		            }
		            
		         }else{ //이전 달력에서 찾아야 하는 경우
		            //이전달에 해당되는 일정들 가져오고
		            if(getyear == getyear_first && getmonth == getmonth_first && getday>= getmonth_first&& getday<=getday_end){
		               result.getSchedulelist().add(listofschedule.get(i));
		               System.out.println("두번째");
		            }
		            //나머지 1일 부터 남은 갯수 만큼 가져오기 
		            if(cur_count>=1){
		               
		               next_month = getmonth_end+1;
		               if(next_month ==13){
		                  next_month =1;
		                  next_year = getyear_end +1;
		                  System.out.println("세번째");
		               }
		               if(getyear == next_year && getmonth == next_month && getday>= 1&& getday<=cur_count){
		                  result.getSchedulelist().add(listofschedule.get(i));
		                  System.out.println("네번째");
		               }
		            }

		         }
		      
		      }
		      
		      return result;
		   
		  
	   }
	
	
}