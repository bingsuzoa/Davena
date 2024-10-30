package com.kkomiding.davena.work.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.kkomiding.davena.holiday.domain.Holiday;
import com.kkomiding.davena.holiday.service.HolidayService;
import com.kkomiding.davena.user.domain.User;
import com.kkomiding.davena.user.service.UserService;
import com.kkomiding.davena.work.domain.Work;
import com.kkomiding.davena.work.dto.ScheduleTable;
import com.kkomiding.davena.work.repository.WorkRepository;


@Service
public class WorkService {
	
	
	private WorkRepository workRepository;
	private UserService userService;
	private HolidayService holidayService;
	
	public WorkService(WorkRepository workRepository
					  ,HolidayService holidayService
					  ,UserService userService) {
		this.workRepository = workRepository;
		this.userService = userService;
		this.holidayService = holidayService;
	}

	
	//회원가입하면 work table에 저장하기
	public Work insertUserId(int userId) {
		
		int roomId = userService.getUser(userId).getRoomId();
		
		Work work = new Work();
					work.setUserId(userId);
					work.setRoomId(roomId);
		return workRepository.save(work);
	}
	
	public List<Work> getWorkList(){
		
		return workRepository.findAll();
	}
	
	
	//휴가 신청하면 work table에 저장하기
	public Work getWork(String type
					   ,LocalDateTime startDay
					   ,LocalDateTime endDay
					   ,int userId) {
	
		
		Optional<Work> optionalWork = workRepository.findByUserId(userId);
		Work work =  optionalWork.orElse(null);
		
		//날짜데이터 내가 원하는 타입에 맞게 수정하기
		int start = startDay.getDayOfMonth();
		
		//마지막날짜 다음달로 넘어가는 경우가 있으니깐; 
		int end = 0;
		LocalDate localDate = endDay.toLocalDate().minusMonths(1);	
		YearMonth month = YearMonth.from(localDate);
		LocalDate endDate = month.atEndOfMonth();	
		if(endDay.getDayOfMonth() == 1) {
			end = endDate.getDayOfMonth();
		} else {			
			end = endDay.getDayOfMonth()-1;
		}
		
		//workentity저장하기
		work.setDay(start, end, type);
		work.setUpdatedAt(LocalDateTime.now());
		Work newWork = workRepository.save(work);
		
		return newWork;		
	}
	
	public List<Work> resetWorkTable(int userId) {
		int roomId = userService.getUser(userId).getRoomId();
		
		List<Work> workList = workRepository.findByRoomId(roomId);
		List<Work> madeList = new ArrayList<>();
		for(Work work : workList) {
			Work resetWork = workRepository.findById(work.getId()).orElse(null);
			resetWork = resetWork.toBuilder()
							.day1(null)
							.day2(null)
							.day3(null)
							.day4(null)
							.day5(null)
							.day6(null)
							.day7(null)
							.day8(null)
							.day9(null)
							.day10(null)
							.day11(null)
							.day12(null)
							.day13(null)
							.day14(null)
							.day15(null)
							.day16(null)
							.day17(null)
							.day18(null)
							.day19(null)
							.day20(null)
							.day21(null)
							.day22(null)
							.day23(null)
							.day24(null)
							.day25(null)
							.day26(null)
							.day27(null)
							.day28(null)
							.day29(null)
							.day30(null)
							.day31(null)
							.build();
//			madeList.add(workRepository.save(resetWork));	
			workRepository.save(resetWork);
		}
		for(Work work : workList) {
			Work resetWork = workRepository.findById(work.getId()).orElse(null);
			int workUserId = work.getUserId();
			List<Holiday> myHolidayList = holidayService.getListByUserIdAndMonth(workUserId);
			for(Holiday holiday : myHolidayList) {
				
				LocalDateTime startDay = holiday.getStartDay();
				LocalDateTime endDay = holiday.getEndDay();
				String type = holiday.getType();			
				
				//날짜데이터 내가 원하는 타입에 맞게 수정하기
				int start = startDay.getDayOfMonth();
				
				//마지막날짜 다음달로 넘어가는 경우가 있으니깐; 
				int end = 0;
				LocalDate localDate = endDay.toLocalDate().minusMonths(1);	
				YearMonth month = YearMonth.from(localDate);
				LocalDate endDate = month.atEndOfMonth();	
				if(endDay.getDayOfMonth() == 1) {
					end = endDate.getDayOfMonth();
				} else {			
					end = endDay.getDayOfMonth()-1;
				}
				
				//workentity저장하기
				resetWork.setDay(start, end, type);
				resetWork.setUpdatedAt(LocalDateTime.now());
				madeList.add(workRepository.save(resetWork));	
				
			}
			
			resetWork = resetWork.toBuilder()
							
							.build();
			madeList.add(workRepository.save(resetWork));			
		}
		return madeList;
	}
	
	
	//이름이랑 근무만 저장할 dto
	public List<ScheduleTable> setWorkSchedule(int userId) {
		
		User user = userService.getUser(userId);
		int roomId = user.getRoomId();
		
		//roomId가 13인 근무자들의 workschedule 리스트조회
		List<Work> workListByRoomId = workRepository.findByRoomId(roomId);

		
		List<ScheduleTable> scheduleTableList = new ArrayList<>();
		//worklist userId로 name조회
			for(Work work : workListByRoomId) {
				String name = userService.getUser(work.getUserId()).getName();
				
				ScheduleTable scheduleTable = ScheduleTable.builder()
															.name(name)
															.day1(work.getDay1())
															.day2(work.getDay2())
															.day3(work.getDay3())
															.day4(work.getDay4())
															.day5(work.getDay5())
															.day6(work.getDay6())
															.day7(work.getDay7())
															.day8(work.getDay8())
															.day9(work.getDay9())
															.day10(work.getDay10())
															.day11(work.getDay11())
															.day12(work.getDay12())
															.day13(work.getDay13())
															.day14(work.getDay14())
															.day15(work.getDay15())
															.day16(work.getDay16())
															.day17(work.getDay17())
															.day18(work.getDay18())
															.day19(work.getDay19())
															.day20(work.getDay20())
															.day21(work.getDay21())
															.day22(work.getDay22())
															.day23(work.getDay23())
															.day24(work.getDay24())
															.day25(work.getDay25())
															.day26(work.getDay26())
															.day27(work.getDay27())
															.day28(work.getDay28())
															.day29(work.getDay29())
															.day30(work.getDay30())
															.day31(work.getDay31())
															.build();
				scheduleTableList.add(scheduleTable);
			}
			return scheduleTableList;
	}
	
	
	

	public List<Work> getWorkArr(int Dduty, int Eduty, int Nduty, int userId) {
		User user = userService.getUser(userId);
		int roomIdByUserId = user.getRoomId();
		int count = workRepository.countByRoomId(roomIdByUserId);
		
		List<String> workList = new ArrayList<>();
		int offCount = count - Dduty - Eduty- Nduty;
		
		//필요한 근무 수 workList에 우선 담기	
		for(int i = 0; i < Dduty; i++) {
			workList.add("Day");
		}
		for(int i = 0; i < Eduty; i++) {
			workList.add("Eve");
		}
		for(int i = 0; i < Nduty; i++) {
			workList.add("Nig");
		}
		for(int i = 0; i < offCount; i++) {
			workList.add("Off");
		}
		
		//1일차 worklist index 랜덤돌리기
		int index[] = new int[Dduty + Eduty + Nduty + offCount];
		Random rand = new Random();
		for(int i = 0; i < workList.size(); i++) {
			index[i] = rand.nextInt(workList.size());
				for(int j = 0; j < i; j++) {
					if(index[i] == index[j]) {
						i--;
					}
				}
		}
		
		//현재날짜, 마지막날짜
		LocalDate now = LocalDate.now();
		YearMonth month = YearMonth.from(now);
		LocalDate lastDate = month.atEndOfMonth();
		int endDay = lastDate.getDayOfMonth();
		
		
		//D E N randomList 만들기
		List<String> randomWorkList = new ArrayList<>();
		Queue<String> que = new LinkedList<String>();
		Queue<String> que2 = new LinkedList<String>();
		for(int i = 0; i< index.length; i++) {
			randomWorkList.add(workList.get(index[i]));
			que.offer(randomWorkList.get(i));
		}
		
		
		//roomId에 해당하는 workList조회해오기
		List<Work> findByRoomIdList = workRepository.findByRoomId(roomIdByUserId);
		
		//임시 저장 배열만들기
		String[] practice = new String[count];
		String[] nextPractice = new String[count];
////////////////////////day1				
			//day1
				for(Work work : findByRoomIdList) {
					//저장할 work행 가져오기
					Optional<Work> optionalWork = workRepository.findByUserId(work.getUserId());
					Work setDayWork = optionalWork.orElse(null);
					//day1 : 연가 신청해놓은 사람부터 찾고, off 넣기
					if(setDayWork.getDay1() == null) {
						continue;
					} else if(setDayWork.getDay1().equals("연가") || setDayWork.getDay1().equals("오프")) {
						while(!que.peek().equals("Off")) {
							que.offer(que.peek());
							que.remove();
							if(que.peek().equals("Off")) {
								break;
							}
						}
						que2.offer(que.peek());
						que.remove();
					}
				}
				//day1 : 그다음 null에는 아무거나 넣기
				for(Work work : findByRoomIdList) {
					//저장할 work행 가져오기
					Optional<Work> optionalWork = workRepository.findByUserId(work.getUserId());
					Work setDayWork = optionalWork.orElse(null);
					//day1 : 연가 신청해놨을때
					if(setDayWork.getDay1() == null) {
						setDayWork.setDay(1, que.peek());
						workRepository.save(setDayWork);
						que2.offer(que.peek());
						que.remove();
					} else if(setDayWork.getDay1().equals("연가") || setDayWork.getDay1().equals("오프")) {
						continue;
					}
				}
////////////////////////day2			
			//day2
				for(int i = 0; i < findByRoomIdList.size(); i++) {
					Work setDayWork = findByRoomIdList.get(i);	
					//null부터 처리
					if(setDayWork.getDay2() == null) {
						//신청한거 없을때 - 전날 Day
						if(setDayWork.getDay1().equals("Day") || setDayWork.getDay1().equals("Off")
								|| setDayWork.getDay1().equals("오프") || setDayWork.getDay1().equals("연가")) {
							practice[i] = que2.peek();
							que.offer(que2.peek());
							que2.remove();
							
						//신청한거 없을때 - 전날 Eve
						} else if(setDayWork.getDay1().equals("Eve")) {
							//선택지가 아예 없을때
							if(!que2.contains("Nig") && !que2.contains("Off") && !que2.contains("Eve")) {
								//한번섞어주고
								que2.offer(que2.peek());
								que2.remove();
								//임시 리스트삭제
								Arrays.fill(practice, null);
								//queue 합치기
								while(que.peek() != null) {
									que2.offer(que.peek());
									que.remove();
								}
								//work 다시 처음부터
								i = 0;
							//선택지 있을때	
							} else {
								while(que2.peek().equals("Day")) {
									que2.offer(que2.peek());
									que2.remove();		
									if(!que2.peek().equals("Day")) {
										break;
									}
								}
								practice[i] = que2.peek();
								que.offer(que2.peek());
								que2.remove();
							}
						//신청한거 없을때 - 전날 Nig
					} else if(setDayWork.getDay1().equals("Nig")) {
							//선택지가 아예 없을때
						if(!que2.contains("Nig") && !que2.contains("Off")) {
							//한번섞어주고
							que.offer(que2.peek());
							que2.remove();
							//임시 리스트삭제
							Arrays.fill(practice, null);
							//queue 합치기
							while(que.peek() != null) {
								que2.offer(que.peek());
								que.remove();
							}
							//work 다시 처음부터
							i = 0;
						//선택지 있을때	
						} else {
							while(que2.peek().equals("Day") || que2.peek().equals("Eve")) {
								que2.offer(que2.peek());
								que2.remove();		
								if(!que2.peek().equals("Day") && que2.peek().equals("Eve")) {
									break;
								}
							}
							practice[i] = que2.peek();
							que.offer(que2.peek());
							que2.remove();
						}
					}
				//연가//오프 신청했을 때
				} else if(setDayWork.getDay2().equals("연가") || setDayWork.getDay2().equals("오프")) {
					//오프를 줘야되는데 오프가 없을때
					if(!que2.contains("Off")) {
						//한번섞어주고
						que2.offer(que2.peek());
						que2.remove();
						//임시 리스트삭제
						Arrays.fill(practice, null);
						//queue 합치기
						while(que.peek() != null) {
							que2.offer(que.peek());
							que.remove();
						}
						//work 다시 처음부터
						i = 0;
					//선택지 있을 때
					} else {
						while(!que2.peek().equals("Off")) {
							que2.offer(que2.peek());
							que2.remove();		
							if(que2.peek().equals("Off")) {
								break;
							}
						}
						practice[i] = setDayWork.getDay2();
						que.offer(que2.peek());
						que2.remove();
					}
				} 
			}
				
			//day2 저장
			int workIndex = 0;
			for(Work work : findByRoomIdList) {
				//저장할 work행 가져오기
				Optional<Work> optionalWork = workRepository.findByUserId(work.getUserId());
				Work setDayWork = optionalWork.orElse(null);
				
				for(int i = workIndex; i < practice.length;) {
					setDayWork.setDay(2, practice[i]);
					workRepository.save(setDayWork);
					break;
				}
				workIndex++;
			}
			//임시 리스트삭제
			Arrays.fill(practice, null);
			
////////////////////////day3
			//day3,4
			for(int i = 3; i <= 5; i++) {
				boolean checkHoliday = false;
				
				while(checkHoliday == false) {
					for(int k = 0; k < findByRoomIdList.size(); k++) {
						//저장할 work행 가져오기
						Work setDayWork = findByRoomIdList.get(k);
						
						//null부터 처리
						if(setDayWork.getDay(i) == null) {
							//무조건 오프줘야할때 - 전날, 전전날 Nig 2개일때
							if(setDayWork.getDay(i-1).equals("Nig") && setDayWork.getDay(i-2).equals("Nig")) {
								if(i%2 != 0) {
									while(!que.peek().equals("Off")) {
										que.offer(que.peek());
										que.remove();		
										if(que.peek().equals("Off")) {
											break;
										}
									}
									practice[k] = que.peek();
									nextPractice[k] = practice[k];
									que2.offer(que.peek());
									que.remove();
								} else {
									while(!que2.peek().equals("Off")) {
										que2.offer(que2.peek());
										que2.remove();		
										if(que2.peek().equals("Off")) {
											break;
										}
									}
									practice[k] = que.peek();
									nextPractice[k] = practice[k];
									que2.offer(que.peek());
									que.remove();
								}
							} else if((setDayWork.getDay(i-1).equals("Off") || setDayWork.getDay(i-1).equals("연가") || setDayWork.getDay(i-1).equals("오프"))
									&&(setDayWork.getDay(i-2).equals("Off") || setDayWork.getDay(i-2).equals("연가") || setDayWork.getDay(i-2).equals("오프"))) {
								if(i%2 != 0) {
									while(que.peek().equals("Off")) {
										que.offer(que.peek());
										que.remove();		
										if(!que.peek().equals("Off")) {
											break;
										}
									}
									practice[k] = que.peek();
									que2.offer(que.peek());
									que.remove();
								} else {
									while(que2.peek().equals("Off")) {
										que2.offer(que2.peek());
										que2.remove();		
										if(!que2.peek().equals("Off")) {
											break;
										}
									}
									practice[k] = que2.peek();
									que.offer(que2.peek());
									que2.remove();
								}	
							} else {
								continue;
							}
						//null 아닐때
							//무조건 오프줘야할때 - 오프나 연가 신청해놨을 때
						} else if(setDayWork.getDay(i).equals("Off") ||setDayWork.getDay(i).equals("연가") 
								||setDayWork.getDay(i).equals("오프")) {
							if(i%2 != 0) {
								while(!que.peek().equals("Off")) {
									que.offer(que.peek());
									que.remove();
									if(que.peek().equals("Off")) {
										break;
									}
								}
								practice[k] = setDayWork.getDay(i);
								que2.offer(que.peek());
								que.remove();
							} else {
								while(!que2.peek().equals("Off")) {
									que2.offer(que2.peek());
									que2.remove();
									if(que2.peek().equals("Off")) {
										break;
									}
								}
								practice[k] = setDayWork.getDay(i);
								que.offer(que2.peek());
								que2.remove();
							}
						}
					}
					checkHoliday = true;
				}
				
					
				for(int k = 0; k < findByRoomIdList.size(); k++) {
					
					//false면 다시 위에서 랜덤 돌려야함
					if(checkHoliday == false) {
						for(int	l = 0; l < findByRoomIdList.size(); l++) {
							//저장할 work행 가져오기
							Work setDayWork = findByRoomIdList.get(l);
							
							//null부터 처리
							if(setDayWork.getDay(i) == null) {
								//무조건 오프줘야할때 - 전날, 전전날 Nig 2개일때
								if(setDayWork.getDay(i-1).equals("Nig") && setDayWork.getDay(i-2).equals("Nig")) {
									if(i%2 != 0) {
										while(!que.peek().equals("Off")) {
											que.offer(que.peek());
											que.remove();		
											if(que.peek().equals("Off")) {
												break;
											}
										}
										practice[l] = que.peek();
										nextPractice[l] = practice[l];
										que2.offer(que.peek());
										que.remove();
									} else {
										while(!que2.peek().equals("Off")) {
											que2.offer(que2.peek());
											que2.remove();		
											if(que2.peek().equals("Off")) {
												break;
											}
										}
										practice[l] = que2.peek();
										nextPractice[l] = practice[l];
										que.offer(que2.peek());
										que2.remove();
									}
								} else if((setDayWork.getDay(i-1).equals("Off") || setDayWork.getDay(i-1).equals("연가") || setDayWork.getDay(i-1).equals("오프"))
										&&(setDayWork.getDay(i-2).equals("Off") || setDayWork.getDay(i-2).equals("연가") || setDayWork.getDay(i-2).equals("오프"))) {
									if(i%2 != 0) {
										while(que.peek().equals("Off")) {
											que.offer(que.peek());
											que.remove();		
											if(!que.peek().equals("Off")) {
												break;
											}
										}
										practice[k] = que.peek();
										que2.offer(que.peek());
										que.remove();
									} else {
										while(que2.peek().equals("Off")) {
											que2.offer(que2.peek());
											que2.remove();		
											if(!que2.peek().equals("Off")) {
												break;
											}
										}
										practice[k] = que2.peek();
										que.offer(que2.peek());
										que2.remove();
									}	
								} else {
									continue;
								}
							//null 아닐때
								//무조건 오프줘야할때 - 오프나 연가 신청해놨을 때
							} else if(setDayWork.getDay(i).equals("Off") ||setDayWork.getDay(i).equals("연가") 
									||setDayWork.getDay(i).equals("오프")) {
								if(i%2 != 0) {
									while(!que.peek().equals("Off")) {
										que.offer(que.peek());
										que.remove();
										if(que.peek().equals("Off")) {
											break;
										}
									}
									practice[l] = setDayWork.getDay(i);
									que2.offer(que.peek());
									que.remove();
								} else {
									while(!que2.peek().equals("Off")) {
										que2.offer(que2.peek());
										que2.remove();
										if(que2.peek().equals("Off")) {
											break;
										}
									}
									practice[l] = setDayWork.getDay(i);
									que.offer(que2.peek());
									que2.remove();
								}
							}
						}
						checkHoliday = true;
					}

					//저장할 work행 가져오기
					Work setDayWork = findByRoomIdList.get(k);
					
					//null부터 처리
					if(setDayWork.getDay(i) == null && practice[k] == null) {
						//전날 Day
						if(setDayWork.getDay(i-1).equals("Day") || setDayWork.getDay(i-1).equals("Off")
								|| setDayWork.getDay(i-1).equals("오프") || setDayWork.getDay(i-1).equals("연가")) {				
							if(i%2 != 0) {
								practice[k] = que.peek();
								que2.offer(que.peek());
								que.remove();
							} else {
								practice[k] = que2.peek();
								que.offer(que2.peek());
								que2.remove();
							}
						// 전날 Eve
						} else if(setDayWork.getDay(i-1).equals("Eve")) {
							if(i%2 != 0) {
								//선택지가 아예 없을때
								if(!que.contains("Nig") && !que.contains("Off") && !que.contains("Eve")) {
									//한번섞어주고
									que.offer(que.peek());
									que.remove();
									//임시 리스트삭제
									Arrays.fill(practice, null);
									//queue 합치기
									while(que2.peek() != null) {
										que.offer(que2.peek());
										que2.remove();
									}
									//work 다시 처음부터
									k = 0;
									checkHoliday = false;
								//선택지 있을때	
								} else {
									while(que.peek().equals("Day")) {
										que.offer(que.peek());
										que.remove();		
										if(!que.peek().equals("Day")) {
											break;
										}
									}
									practice[k] = que.peek();
									que2.offer(que.peek());
									que.remove();
								}	
							} else {
								//선택지가 아예 없을때
								if(!que2.contains("Nig") && !que2.contains("Off") && !que2.contains("Eve")) {
									//한번섞어주고
									que2.offer(que2.peek());
									que2.remove();
									//임시 리스트삭제
									Arrays.fill(practice, null);
									//queue 합치기
									while(que.peek() != null) {
										que2.offer(que.peek());
										que.remove();
									}
									//work 다시 처음부터
									k = 0;
									checkHoliday = false;
								//선택지 있을때	
								} else {
									while(que2.peek().equals("Day")) {
										que2.offer(que2.peek());
										que2.remove();		
										if(!que2.peek().equals("Day")) {
											break;
										}
									}
									practice[k] = que2.peek();
									que.offer(que2.peek());
									que2.remove();
								}	
							}						
						//전날 Nig
						} else if(setDayWork.getDay(i-1).equals("Nig")) {
							//나이트 연속 2개인경우 : 이미 정해놨음 continue;
							if(setDayWork.getDay(i-2).equals("Nig")) {
								continue;
							//나이트 연속 2개 아닌경우
							} else {
								if(i % 2 != 0) {								
									//선택지가 없을때
									if(!que.contains("Off") && !que.contains("Nig")) {
										//한번섞어주고
										que.offer(que.peek());
										que.remove();
										//임시 리스트삭제
										Arrays.fill(practice, null);
										//queue 합치기
										while(que2.peek() != null) {
											que.offer(que2.peek());
											que2.remove();
										}
										//work 다시 처음부터
										k = 0;
										checkHoliday = false;
										//선택지가 있을때
									} else {
										while(!que.peek().equals("Off") && !que.contains("Nig")) {
											que.offer(que.peek());
											que.remove();		
											if(que.peek().equals("Off") || que.contains("Nig")) {
												break;
											}
										}
										practice[k] = que.peek();
										que2.offer(que.peek());
										que.remove();
									}
								} else {
									//선택지가 없을때
									if(!que2.contains("Off") && !que2.contains("Nig")) {
										//한번섞어주고
										que2.offer(que2.peek());
										que2.remove();
										//임시 리스트삭제
										Arrays.fill(practice, null);
										//queue 합치기
										while(que.peek() != null) {
											que2.offer(que.peek());
											que.remove();
										}
										//work 다시 처음부터
										k = 0;
										checkHoliday = false;
										//선택지가 있을때
									} else {
										while(!que2.peek().equals("Off") && !que2.contains("Nig")) {
											que2.offer(que2.peek());
											que2.remove();		
											if(que2.peek().equals("Off") || que2.contains("Nig")) {
												break;
											}
										}
										practice[k] = que2.peek();
										que.offer(que2.peek());
										que2.remove();
									}
								}	
							}
						}	
					} else {
						continue;
					}
				}
				//day3 저장
				int workIndex2 = 0;
				for(Work work2 : findByRoomIdList) {
					//저장할 work행 가져오기
					Optional<Work> optionalWork2 = workRepository.findByUserId(work2.getUserId());
					Work setDayWork2 = optionalWork2.orElse(null);
					
					for(int j = workIndex2; j <findByRoomIdList.size();) {
						setDayWork2.setDay(i, practice[j]);
						workRepository.save(setDayWork2);
						if(nextPractice[j] == null) {
							workIndex2++;
							break;
						} else {
							setDayWork2.setDay(i+1, nextPractice[j]);
							workRepository.save(setDayWork2);
							workIndex2++;
							break;
						}
					}
				}
				//임시 리스트삭제
				Arrays.fill(nextPractice, null);
				Arrays.fill(practice, null);
			}

////////////////////////day6~
			for(int i = 6; i <= endDay; i++) {
				boolean checkHoliday = false;
				
				while(checkHoliday == false) {
					for(int k = 0; k < findByRoomIdList.size(); k++) {
						//저장할 work행 가져오기
						Work setDayWork = findByRoomIdList.get(k);
						
						//null부터 처리
						if(setDayWork.getDay(i) == null) {
							//무조건 오프줘야할때 - 전날, 전전날 Nig 2개일때
							if(setDayWork.getDay(i-1).equals("Nig") && setDayWork.getDay(i-2).equals("Nig")) {
								if(i%2 != 0) {
									while(!que.peek().equals("Off")) {
										que.offer(que.peek());
										que.remove();		
										if(que.peek().equals("Off")) {
											break;
										}
									}
									practice[k] = que.peek();
									nextPractice[k] = practice[k];
									que2.offer(que.peek());
									que.remove();
								} else {
									while(!que2.peek().equals("Off")) {
										que2.offer(que2.peek());
										que2.remove();		
										if(que2.peek().equals("Off")) {
											break;
										}
									}
									practice[k] = que2.peek();
									nextPractice[k] = practice[k];
									que.offer(que2.peek());
									que2.remove();
								}
							} else if((setDayWork.getDay(i-1).equals("Day") || setDayWork.getDay(i-1).equals("Eve") || setDayWork.getDay(i-1).equals("Nig"))
								   && (setDayWork.getDay(i-2).equals("Day") || setDayWork.getDay(i-2).equals("Eve") || setDayWork.getDay(i-2).equals("Nig"))
								   && (setDayWork.getDay(i-3).equals("Day") || setDayWork.getDay(i-3).equals("Eve") || setDayWork.getDay(i-3).equals("Nig"))
								   && (setDayWork.getDay(i-4).equals("Day") || setDayWork.getDay(i-4).equals("Eve") || setDayWork.getDay(i-4).equals("Nig"))
								   && (setDayWork.getDay(i-5).equals("Day") || setDayWork.getDay(i-5).equals("Eve") || setDayWork.getDay(i-5).equals("Nig"))){
									if(i%2 != 0) {
										while(!que.peek().equals("Off")) {
											que.offer(que.peek());
											que.remove();		
											if(que.peek().equals("Off")) {
												break;
											}
										}
										practice[k] = que.peek();
										nextPractice[k] = practice[k];
										que2.offer(que.peek());
										que.remove();
									} else {
										while(!que2.peek().equals("Off")) {
											que2.offer(que2.peek());
											que2.remove();		
											if(que2.peek().equals("Off")) {
												break;
											}
										}
										practice[k] = que2.peek();
										nextPractice[k] = practice[k];
										que.offer(que2.peek());
										que2.remove();
									}						
							} else if((setDayWork.getDay(i-1).equals("Off") || setDayWork.getDay(i-1).equals("연가") || setDayWork.getDay(i-1).equals("오프"))
									&&(setDayWork.getDay(i-2).equals("Off") || setDayWork.getDay(i-2).equals("연가") || setDayWork.getDay(i-2).equals("오프"))) {
								if(i%2 != 0) {
									while(que.peek().equals("Off")) {
										que.offer(que.peek());
										que.remove();		
										if(!que.peek().equals("Off")) {
											break;
										}
									}
									practice[k] = que.peek();
									que2.offer(que.peek());
									que.remove();
								} else {
									while(que2.peek().equals("Off")) {
										que2.offer(que2.peek());
										que2.remove();		
										if(!que2.peek().equals("Off")) {
											break;
										}
									}
									practice[k] = que2.peek();
									que.offer(que2.peek());
									que2.remove();
								}	
							} else {
								continue;
							}
						//null 아닐때
							//무조건 오프줘야할때 - 오프나 연가 신청해놨을 때
						} else if(setDayWork.getDay(i).equals("Off") ||setDayWork.getDay(i).equals("연가") 
								||setDayWork.getDay(i).equals("오프")) {
							if(i%2 != 0) {
								while(!que.peek().equals("Off")) {
									que.offer(que.peek());
									que.remove();
									if(que.peek().equals("Off")) {
										break;
									}
								}
								practice[k] = setDayWork.getDay(i);
								que2.offer(que.peek());
								que.remove();
							} else {
								while(!que2.peek().equals("Off")) {
									que2.offer(que2.peek());
									que2.remove();
									if(que2.peek().equals("Off")) {
										break;
									}
								}
								practice[k] = setDayWork.getDay(i);
								que.offer(que2.peek());
								que2.remove();
							}
						}
					}
					checkHoliday = true;
				}
				
			for(int k = 0; k < findByRoomIdList.size(); k++) {
				//저장할 work행 가져오기
				Work setDayWork = findByRoomIdList.get(k);
				
				//null부터 처리
				if(setDayWork.getDay(i) == null && practice[k] == null) {
					//전날 Day
					if(setDayWork.getDay(i-1).equals("Day") || setDayWork.getDay(i-1).equals("Off")
							|| setDayWork.getDay(i-1).equals("오프") || setDayWork.getDay(i-1).equals("연가")) {				
						if(i%2 != 0) {
							practice[k] = que.peek();
							que2.offer(que.peek());
							que.remove();
						} else {
							practice[k] = que2.peek();
							que.offer(que2.peek());
							que2.remove();
						}
					// 전날 Eve
					} else if(setDayWork.getDay(i-1).equals("Eve")) {
						if(i%2 != 0) {
							//선택지가 아예 없을때
							if(!que.contains("Nig") && !que.contains("Off") && !que.contains("Eve")) {
								//한번섞어주고
								que.offer(que.peek());
								que.remove();
								//임시 리스트삭제
								Arrays.fill(practice, null);
								//queue 합치기
								while(que2.peek() != null) {
									que.offer(que2.peek());
									que2.remove();
								}
								//work 다시 처음부터
								k = 0;
								checkHoliday = false;
							//선택지 있을때	
							} else {
								while(que.peek().equals("Day")) {
									que.offer(que.peek());
									que.remove();		
									if(!que.peek().equals("Day")) {
										break;
									}
								}
								practice[k] = que.peek();
								que2.offer(que.peek());
								que.remove();
							}	
						} else {
							//선택지가 아예 없을때
							if(!que2.contains("Nig") && !que2.contains("Off") && !que2.contains("Eve")) {
								//한번섞어주고
								que2.offer(que2.peek());
								que2.remove();
								//임시 리스트삭제
								Arrays.fill(practice, null);
								//queue 합치기
								while(que.peek() != null) {
									que2.offer(que.peek());
									que.remove();
								}
								//work 다시 처음부터
								k = 0;
								checkHoliday = false;
							//선택지 있을때	
							} else {
								while(que2.peek().equals("Day")) {
									que2.offer(que2.peek());
									que2.remove();		
									if(!que2.peek().equals("Day")) {
										break;
									}
								}
								practice[k] = que2.peek();
								que.offer(que2.peek());
								que2.remove();
							}	
						}						
					//전날 Nig
					} else if(setDayWork.getDay(i-1).equals("Nig")) {
						//나이트 연속 2개인경우 : 이미 정해놨음 continue;
						if(setDayWork.getDay(i-2).equals("Nig")) {
							continue;
						//나이트 연속 2개 아닌경우
						} else {
							if(i % 2 != 0) {								
								//선택지가 없을때
								if(!que.contains("Off") && !que.contains("Nig")) {
									//한번섞어주고
									que.offer(que.peek());
									que.remove();
									//임시 리스트삭제
									Arrays.fill(practice, null);
									//queue 합치기
									while(que2.peek() != null) {
										que.offer(que2.peek());
										que2.remove();
									}
									//work 다시 처음부터
									k = 0;
									checkHoliday = false;
									//선택지가 있을때
								} else {
									while(!que.peek().equals("Off") && !que.contains("Nig")) {
										que.offer(que.peek());
										que.remove();		
										if(que.peek().equals("Off") || que.contains("Nig")) {
											break;
										}
									}
									practice[k] = que.peek();
									que2.offer(que.peek());
									que.remove();
								}
							} else {
								//선택지가 없을때
								if(!que2.contains("Off") && !que2.contains("Nig")) {
									//한번섞어주고
									que2.offer(que2.peek());
									que2.remove();
									//임시 리스트삭제
									Arrays.fill(practice, null);
									//queue 합치기
									while(que.peek() != null) {
										que2.offer(que.peek());
										que.remove();
									}
									//work 다시 처음부터
									k = 0;
									checkHoliday = false;
									//선택지가 있을때
								} else {
									while(!que2.peek().equals("Off") && !que2.contains("Nig")) {
										que2.offer(que2.peek());
										que2.remove();		
										if(que2.peek().equals("Off") || que2.contains("Nig")) {
											break;
										}
									}
									practice[k] = que2.peek();
									que.offer(que2.peek());
									que2.remove();
								}
							}	
						}
					}	
				} else {
					continue;
				}
			}
			
				//day6~ 저장
				int workIndex3 = 0;
				for(Work work3 : findByRoomIdList) {
					//저장할 work행 가져오기
					Optional<Work> optionalWork3 = workRepository.findByUserId(work3.getUserId());
					Work setDayWork3 = optionalWork3.orElse(null);
					
					for(int j = workIndex3; j <findByRoomIdList.size();) {
						setDayWork3.setDay(i, practice[j]);
						workRepository.save(setDayWork3);
						if(nextPractice[j] == null) {
							workIndex3++;
							break;
						} else {
							setDayWork3.setDay(i+1, nextPractice[j]);
							workRepository.save(setDayWork3);
							workIndex3++;
							break;
						}
					}
				}
				//임시 리스트삭제
				Arrays.fill(nextPractice, null);
				Arrays.fill(practice, null);
				
			}	
		return findByRoomIdList;
	}	
}
