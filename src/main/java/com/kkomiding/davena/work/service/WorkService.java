package com.kkomiding.davena.work.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

import org.springframework.stereotype.Service;

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
	
	public WorkService(WorkRepository workRepository
					  ,HolidayService holidayService
					  ,UserService userService) {
		this.workRepository = workRepository;
		this.userService = userService;
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
		List<String> workList = new ArrayList<>();
		int index[] = new int[Dduty + Eduty + Nduty];
				
		
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
		
		//현재날짜, 마지막날짜
		LocalDate now = LocalDate.now();
		YearMonth month = YearMonth.from(now);
		LocalDate lastDate = month.atEndOfMonth();
		int endDay = lastDate.getDayOfMonth();
		
		//1일차 worklist를 random돌리기 : randomWorkList 
		Random rand = new Random();
		for(int i = 0; i < workList.size(); i++) {
			index[i] = rand.nextInt(workList.size());
				for(int j = 0; j < i; j++) {
					if(index[i] == index[j]) {
						i--;
					}
				}
		}
		
		//queue에 담기
		List<String> randomWorkList = new ArrayList<>();
		Queue<String> que = new LinkedList<String>();
		Queue<String> que2 = new LinkedList<String>();
		for(int i = 0; i< index.length; i++) {
			randomWorkList.add(workList.get(index[i]));
			que.offer(workList.get(index[i]));
		}
		
		int roomId = userService.getUser(userId).getRoomId();
		
		//roomId에 해당하는 workList조회해오기
		List<Work> findByRoomIdList = workRepository.findByRoomId(roomId);
				//day1
				for(Work work : findByRoomIdList) {
					//저장할 work행 가져오기
					Optional<Work> optionalWork = workRepository.findByUserId(work.getUserId());
					Work setDayWork = optionalWork.orElse(null);
					//day1 : 아무것도 없을때 -> 모든 듀티 가능
					if(setDayWork.getDay1() == null) {
						setDayWork.setDay(1, que.peek());
						workRepository.save(setDayWork);
						que2.offer(que.peek());
						que.remove();
					//day1 : 연가, 오프가 저장되어있을 때 -> continue 
					} else {
						if(setDayWork.getDay1().equals("연가") || setDayWork.getDay1().equals("오프")) {
							continue;
						} else {
							setDayWork.setDay(1, que.peek());
							workRepository.save(setDayWork);
							que2.offer(que.peek());
							que.remove();
						}
					}
				}
				//day2, 전날 근무표에 따라 근무 제한 조건이 있음
				for(Work work : findByRoomIdList) {
					//저장할 work행 가져오기
					Optional<Work> optionalWork = workRepository.findByUserId(work.getUserId());
					Work setDayWork = optionalWork.orElse(null);
					//day2 : 아무것도 없을때 + 전날 day, 연가, 오프 -> 모든 듀티 가능
					if(setDayWork.getDay2() == null) {
						if(setDayWork.getDay1().equals("Day") || setDayWork.getDay1().equals("연가") || setDayWork.getDay1().equals("오프")) {
							setDayWork.setDay(2, que2.peek());
							workRepository.save(setDayWork);
							que.offer(que2.peek());
							que2.remove();
						}
					//day2 : 아무것도 없을때 + 전날 eve -> day 불가
						else if(setDayWork.getDay1().equals("Eve")) {
							String duty = que2.peek();
							if(duty.equals("Day")) {
							que2.offer(que2.peek());
							que2.remove();
							} else {
							setDayWork.setDay(2, que2.peek());
							workRepository.save(setDayWork);
							que.offer(que2.peek());
							que2.remove();
							}
						}
						//day2 : 아무것도 없을때 + 전날 night -> eve, day 불가
						else if(setDayWork.getDay1().equals("Night") || setDayWork.getDay1().equals("Nig")) {
							String duty = que2.peek();
							if(duty.equals("Day") || duty.equals("Eve")) {
								que2.offer(que2.peek());
								que2.remove();
							} else {
								setDayWork.setDay(2, que2.peek());
								workRepository.save(setDayWork);
								que.offer(que2.peek());
								que2.remove();
							}
						}
						//day2 : 연가나 오프인경우 -> continue
					} else {
						if(setDayWork.getDay2().equals("연가") || setDayWork.getDay2().equals("오프")) {
							continue;
						} else {
							if(setDayWork.getDay1().equals("Day") || setDayWork.getDay1().equals("연가") || setDayWork.getDay1().equals("오프")) {
								setDayWork.setDay(2, que2.peek());
								workRepository.save(setDayWork);
								que.offer(que2.peek());
								que2.remove();
							}
						//day2 : 아무것도 없을때 + 전날 eve -> day 불가
							else if(setDayWork.getDay1().equals("Eve")) {
								String duty = que2.peek();
								if(duty.equals("Day")) {
								que2.offer(que2.peek());
								que2.remove();
								} else {
								setDayWork.setDay(2, que2.peek());
								workRepository.save(setDayWork);
								que.offer(que2.peek());
								que2.remove();
								}
							}
							//day2 : 아무것도 없을때 + 전날 night -> eve, day 불가
							else if(setDayWork.getDay1().equals("Night") || setDayWork.getDay1().equals("Nig")) {
								String duty = que2.peek();
								if(duty.equals("Day") || duty.equals("Eve")) {
									que2.offer(que2.peek());
									que2.remove();
								} else {
									setDayWork.setDay(2, que2.peek());
									workRepository.save(setDayWork);
									que.offer(que2.peek());
									que2.remove();
								}
							}
						}
					}	
				}
			

				//day3~ , 전날 + 전전날 근무표에 따라 근무제한 조건이 있음
					for(int i = 3; i <= endDay; i++) {
						for(Work work : findByRoomIdList) {
							//저장할 work행 가져오기
							Optional<Work> optionalWork = workRepository.findByUserId(work.getUserId());
							Work setDayWork = optionalWork.orElse(null);
						
							//당일 : 아무것도 없을때 	
							if(setDayWork.getDay(i) == null) {
								// 전날 day, 연가, 오프 -> 모든 듀티 가능	
								if (setDayWork.getDay(i-1).equals("Day") || setDayWork.getDay(i-1).equals("연가") || setDayWork.getDay(i-1).equals("오프")) {
									//off, eve, night 올 수 있음
									if(i%2 != 0) {
										setDayWork.setDay(i, que.peek());
										workRepository.save(setDayWork);
										que2.offer(que.peek());
										que.remove();
									} else {
										setDayWork.setDay(i, que2.peek());
										workRepository.save(setDayWork);
										que.offer(que2.peek());
										que2.remove();
									}
								}
								// 전날 eve -> day 불가
								else if(setDayWork.getDay(i-1).equals("Eve")) {
									//day 올수없음
									if(i%2 != 0) {
										String duty = que.peek();
										if(duty.equals("Day")) {
											que.offer(que.peek());
											que.remove();
										} else {
											setDayWork.setDay(i, que.peek());
											workRepository.save(setDayWork);
											que2.offer(que.peek());
											que.remove();
										}
									} else {
										String duty = que2.peek();
										if(duty.equals("Day")) {
											que2.offer(que2.peek());
											que2.remove();
										} else {
											setDayWork.setDay(i, que2.peek());
											workRepository.save(setDayWork);
											que.offer(que2.peek());
											que2.remove();
										}
									}
								}
								// 전날 night
								else if(setDayWork.getDay(i-1).equals("Nig")) {
									//나이트 2개 연속
									if(setDayWork.getDay(i-2).equals("Nig")) {
										setDayWork.setDay(i, "오프");
										setDayWork.setDay(i+1, "오프");
										workRepository.save(setDayWork);
									} else {
									//나이트 2개 연속 아닌경우
										if(i%2 != 0) {
											String duty = que.peek();
											if(duty.equals("Eve") || duty.equals("Day")) {
												que.offer(que.peek());
												que.remove();
											} else {
												setDayWork.setDay(i, que.peek());
												workRepository.save(setDayWork);
												que2.offer(que.peek());
												que.remove();
											}
										} else {
											String duty = que2.peek();
											if(duty.equals("Eve") || duty.equals("Day")) {
												que2.offer(que2.peek());
												que2.remove();
											} else {
												setDayWork.setDay(i, que2.peek());
												workRepository.save(setDayWork);
												que.offer(que2.peek());
												que2.remove();
											}
										}	
									}
								}
							} else  {
								if(setDayWork.getDay(i).equals("연가") || setDayWork.getDay(i).equals("오프")) {
									continue;
								} else {
									// 전날 day, 연가, 오프 -> 모든 듀티 가능	
									if (setDayWork.getDay(i-1).equals("Day") || setDayWork.getDay(i-1).equals("연가") || setDayWork.getDay(i-1).equals("오프")) {
										//off, eve, night 올 수 있음
										if(i%2 != 0) {
											setDayWork.setDay(i, que.peek());
											workRepository.save(setDayWork);
											que2.offer(que.peek());
											que.remove();
										} else {
											setDayWork.setDay(i, que2.peek());
											workRepository.save(setDayWork);
											que.offer(que2.peek());
											que2.remove();
										}
									}
									// 전날 eve -> day 불가
									else if(setDayWork.getDay(i-1).equals("Eve")) {
										//day 올수없음
										if(i%2 != 0) {
											String duty = que.peek();
											if(duty.equals("Day")) {
												que.offer(que.peek());
												que.remove();
											} else {
												setDayWork.setDay(i, que.peek());
												workRepository.save(setDayWork);
												que2.offer(que.peek());
												que.remove();
											}
										} else {
											String duty = que2.peek();
											if(duty.equals("Day")) {
												que2.offer(que2.peek());
												que2.remove();
											} else {
												setDayWork.setDay(i, que2.peek());
												workRepository.save(setDayWork);
												que.offer(que2.peek());
												que2.remove();
											}
										}
									}
									// 전날 night
									else if(setDayWork.getDay(i-1).equals("Nig")) {
										//나이트 2개 연속
										if(setDayWork.getDay(i-2).equals("Nig")) {
											setDayWork.setDay(i, "오프");
											setDayWork.setDay(i+1, "오프");
											workRepository.save(setDayWork);
										} else {
										//나이트 2개 연속 아닌경우
											if(i%2 != 0) {
												String duty = que.peek();
												if(duty.equals("Eve") || duty.equals("Day")) {
													que.offer(que.peek());
													que.remove();
												} else {
													setDayWork.setDay(i, que.peek());
													workRepository.save(setDayWork);
													que2.offer(que.peek());
													que.remove();
												}
											} else {
												String duty = que2.peek();
												if(duty.equals("Eve") || duty.equals("Day")) {
													que2.offer(que2.peek());
													que2.remove();
												} else {
													setDayWork.setDay(i, que2.peek());
													workRepository.save(setDayWork);
													que.offer(que2.peek());
													que2.remove();
												}
											}	
										}
									}
								}
							}	
						}
					}
		
		return findByRoomIdList;
	}	
}
