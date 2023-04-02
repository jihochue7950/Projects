package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.dom4j.Branch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.example.demo.entity.ApiKey;
import com.example.demo.entity.Book;
import com.example.demo.entity.BooksBranch;
import com.example.demo.entity.Branches;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Category;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import com.example.demo.repository.ApiKeyRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BooksBranchRepository;
import com.example.demo.repository.BranchRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.UserRepository;

@DataJpaTest(showSql = false)
//데이터 베이스의 데이터가 더 우세 하니 바꾸지 말아라
@AutoConfigureTestDatabase(replace = Replace.NONE)
//에디터에서 데이터베이스로 데이터 넣기가 가능해짐
@Rollback(false)
public class InputDefaultDatabase {

	@Autowired
	BookRepository bookRepo;
	@Autowired
	CategoryRepository caRepo;
	@Autowired
	BranchRepository branchRepo;
	@Autowired
	BooksBranchRepository bookBranchRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	PaymentRepository payRepo;
	@Autowired
	CartRepository cartRepo;
	@Autowired
	ApiKeyRepository apiRepo;

	@Test
	void CreateDefaultDatabase() {

		Category category000 = new Category("국내도서");
		caRepo.save(category000);
		Category category100 = new Category("외국도서");
		caRepo.save(category100);

		caRepo.save(new Category("건강/취미", category000));
		caRepo.save(new Category("경제경영", category000));
		caRepo.save(new Category("공무원 수험서", category000));
		caRepo.save(new Category("과학", category000));
		caRepo.save(new Category("달력/기타", category000));
		caRepo.save(new Category("대학교재", category000));
		caRepo.save(new Category("만화", category000));
		caRepo.save(new Category("사회과학", category000));
		caRepo.save(new Category("소설/시/희곡", category000));
		caRepo.save(new Category("수험서/자격증", category000));
		caRepo.save(new Category("어린이", category000));
		caRepo.save(new Category("에세이", category000));
		caRepo.save(new Category("여행", category000));
		caRepo.save(new Category("역사", category000));
		caRepo.save(new Category("예술/대중문화", category000));
		caRepo.save(new Category("외국어", category000));
		caRepo.save(new Category("요리/살림", category000));
		caRepo.save(new Category("유아", category000));
		caRepo.save(new Category("인문학", category000));
		caRepo.save(new Category("자기계발", category000));
		caRepo.save(new Category("잡지", category000));
		caRepo.save(new Category("장르소설", category000));
		caRepo.save(new Category("전집/중고전집", category000));
		caRepo.save(new Category("종교/역학", category000));
		caRepo.save(new Category("좋은부모", category000));
		caRepo.save(new Category("청소년", category000));
		caRepo.save(new Category("컴퓨터/모바일", category000));
		caRepo.save(new Category("초등학교참고서", category000));
		caRepo.save(new Category("중학교참고서", category000));
		caRepo.save(new Category("고등학교참고서", category000));

		caRepo.save(new Category("영미도서", category100));
		caRepo.save(new Category("ETL/어학/사전", category100));
		caRepo.save(new Category("건강/스포츠", category100));
		caRepo.save(new Category("경제경영", category100));
		caRepo.save(new Category("공예/취미/수집", category100));
		caRepo.save(new Category("만화", category100));
		caRepo.save(new Category("소설/시/희곡", category100));
		caRepo.save(new Category("여행", category100));
		caRepo.save(new Category("역사", category100));
		caRepo.save(new Category("요리", category100));
		caRepo.save(new Category("인문/사회", category100));
		caRepo.save(new Category("종교/명상/점술", category100));
		caRepo.save(new Category("청소년", category100));
		caRepo.save(new Category("해외잡지", category100));
		caRepo.save(new Category("대학교재/전문서", category100));
		caRepo.save(new Category("건축/디자인", category100));
		caRepo.save(new Category("교육/자료", category100));
		caRepo.save(new Category("기술공학", category100));
		caRepo.save(new Category("법률", category100));
		caRepo.save(new Category("수험서", category100));
		caRepo.save(new Category("언어학", category100));
		caRepo.save(new Category("예술/대중문화", category100));
		caRepo.save(new Category("의학", category100));
		caRepo.save(new Category("자연과학", category100));
		caRepo.save(new Category("컴퓨터", category100));
		caRepo.save(new Category("기타언어권", category100));
		caRepo.save(new Category("독일 도서", category100));
		caRepo.save(new Category("스페인 도서", category100));
		caRepo.save(new Category("중국 도서", category100));
		caRepo.save(new Category("어린이", category100));
		caRepo.save(new Category("그림책", category100));
		caRepo.save(new Category("동화책", category100));
		caRepo.save(new Category("리더스", category100));
		caRepo.save(new Category("영어학습", category100));
		caRepo.save(new Category("챕터북", category100));
		caRepo.save(new Category("코스북", category100));

		branchRepo.save(new Branches("BookQuest 강남점", "서울 강남구 강남대로 388", 37.4974321151032, 127.02838169552845));
		branchRepo.save(new Branches("BookQuest 시청점", "서울 중구 세종대로 지하 101", 37.564663964738195, 126.978106746564));
		branchRepo.save(new Branches("BookQuest 노량진점", "서울 동작구 노량진로 138", 37.51317948453074, 126.94122547645269));
		branchRepo.save(new Branches("BookQuest 부산 W스퀘어점", "부산 남구 분포로 145 더블유스퀘어동", 35.13356779884351, 129.11356988948617));

	}

	@Test
	public void saveNewUser() {

		User user = new User();
		user.setEmail("3");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode("3"));
		user.setAddress1("주소");
		user.setName("하민수");
		user.setPhone("010전화번호");
		user.setRole("admin");
		user.setEnabled(true);
		user.setSignupDate(LocalDateTime.now());
		userRepo.save(user);

		Payment payment = new Payment();
		payment.setBank("신한카드");
		payment.setAccountNumber("1234-0456-7189-1005");
		payment.setExternalPayment("mycard");
		payment.setUser(user);
		payRepo.save(payment);
	}
	
   @Test
   public void saveNewUsertest() {
      User user = new User();
      user.setEmail("royce924@naver.com");      
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      user.setPassword(passwordEncoder.encode("12345678"));
      user.setAddress1("고덕동");
      user.setName("오선용");
      user.setPhone("0109959240");
      user.setPhoto("프로필사진");
      user.setRole("nomal");
      user.setEnabled(true);
      user.setSignupDate(LocalDateTime.now());
      userRepo.save(user);

      Payment payment = new Payment();
      payment.setBank("국민카드");
      payment.setAccountNumber("1234-0416-7189-1005");
      payment.setExternalPayment("mycard");
      payment.setUser(user);
      payRepo.save(payment);
   }
	
		
	@Test
	public void saveNewBook() {
		
		bookRepo.save(new Book("강영현","“주식의 시간은 따로 있다!”\r\n여의도 1타 브로커 강영현이 공개하는\r\n2023 혼돈의 시장을 돌파할 최강 투자 바이블!",0,"2.jpg",19800,"2023-03-13","21세기북스","살 때,팔 때,벌 때",4,0));
        bookRepo.save(new Book("박순혁","알만한 이들은 모두가 기다리고 고대하던\r\n박순혁 이사의 배터리 시장에 대한 단 한 권의 책!",0,"3.jpg",17100,"2023-01-13","지와인","K 배터리 레볼루션",4,0));
        bookRepo.save(new Book("신민철","“13년, 1만 시간을 담은 〈멘탈이 전부다〉 ‘처리형’의 첫 책!”",0,"4.jpg",18900,"2021-03-13","베가북스","돈의 규칙",4,0));
        bookRepo.save(new Book("정재호","중고가 수십만 원에 거래되는 전설의 투자서!\r\n대한민국 대표 주식 전문가 부자아빠 시세관의 정수",0,"5.jpg",19800,"2022-01-13","프런트페이지","주식 시세의 비밀",4,0));
        bookRepo.save(new Book("홍종호","“세상의 흐름을 통찰하고 싶은\r\n모든 청년들에게 일독을 권한다!”",0,"6.jpg",18000,"2022-02-13","다산북스","기후위기부의대전환",4,0));
        bookRepo.save(new Book("김민성","이 책에서 그는 어떻게 성공한 사람처럼 보이는지, 그리고 사람들의 마음을 어떤 식으로 사로잡는지에 대한 구체적인 방법을 다루고 있다.",0,"8.jpg",14220,"2021-06-13","마인드셋","지금 당장 포르쉐를 타라",4,0));
        bookRepo.save(new Book("최한철","월가에서 산전수전 겪은 ‘찐 전문가’의 이기는 투자론\r\n뼈아픈 손실을 겪고 다시 출발선에 선 당신을 위한 제2라운드 지침서",0,"9.jpg",16200,"2021-09-13","에프엔미디어","제2라운드 투자수업",4,0));
        bookRepo.save(new Book("이승환","메타버스와 웹 3.0 시대, 돈의 흐름이 바뀐다!\r\n앞으로 10년, 디지털 변화의 큰 방향을 이해하고 투자전략을 세워라!",0,"디지털 부의 미래.jpg",17100,"2021-10-13","위너스북","디지털 부의미래",4,0));
        bookRepo.save(new Book("문성후","“오래도록 존경받는 리더의 힘은 태도에서 나온다.”",0,"리더의태도.jpg",15750,"2023-01-13","카시오페아","리더의 태도",4,0));
        bookRepo.save(new Book("최인욱","10분기 연속 슈퍼호스트 선정!\r\n500명의 호스트를 코칭한 에어비앤비 공식 슈퍼호스트가 알려주는\r\n에어비앤비 시작과 운영의 모든 것",0,"돈이 되는 공간.jpg",15300,"2020-06-11","파지트","돈이 되는 공간",4,0));
        bookRepo.save(new Book("박정부","“천 원을 경영해야, 3조를 경영할 수 있다!”\r\n전국 1,500개 매장에 하루 100만 명이 찾는 국민가게 다이소,\r\n창업주 박정부 회장이 최초로 직접 밝힌 ‘천 원 경영’ 성공비결",0,"천 원을 경영하라.jpg",14400,"2013-05-13","쌤얀파커스","천 원을 경영하라",4,0));
        bookRepo.save(new Book("김승호","돈에 대한 기존의 생각을 과감히 수정하다!",0,"돈의 속성.jpg",16020,"2020-01-13","스노우폭스북스","돈의 속성",4,0));
        bookRepo.save(new Book("권은진","유튜브 리치맘 라이프의\r\n상위 1%를 만드는 부자 시스템",0,"부의삼각형.jpg",18000,"2022-03-15","북스고","부의 삼각형",4,0));
        bookRepo.save(new Book("배문성","부동산시장에 휘몰아친 고금리 리스크와\r\n유동성 위기를 어떻게 극복할 것인가?\r\n전례 없는 집값 상승과 하강, 그 변곡점의 순간에\r\n반드시 공부해야 할 자산수호 독법(讀法)!",0,"부동산을 공부할 결심.jpg",22500,"2023-03-13","어바웃어북","부동산을 공부할 결심",4,0));
        bookRepo.save(new Book("전형진","33만 구독자 〈집코노미TV〉 진행, 〈한국경제〉 부동산부 전형진 기자의 첫 책\r\n단 한 채의 집이 없는 당신에게 건네는 단 한 권의 부동산 입문서",0,"인생 첫 부동산 공부.jpg",17100,"2021-04-13","알에치코리아","인생 첫 부동산 공부",4,0));
        bookRepo.save(new Book("김성일","“시황 상관없이 저절로 성장하는\r\n시스템을 구축하라!”\r\n현금을 주식에 몰빵한 개미들을 위한 엑시트 시나리오",0,"마법의 투자 시나리오.jpg",18000,"2020-03-13","다산북스","마법의 투자 시나리오",4,0));
        bookRepo.save(new Book("강환국","게을러도, 바쁜 사람도 꾸준히 수익 내고 자산을 지켜주는 마음 편한 퀀트 투자법\r\n무작정 따라하기 X 퀀트 선구자 강환국이 뭉쳤다! 국내 최초 퀀트 투자 입문서",0,"퀸트 투자 무작정 따라하기.jpg",22500,"2023-03-13","길벗","퀸트 투자 무작정따라하기",4,0));
        bookRepo.save(new Book("고재홍","“이 책에서 많은 힌트를 얻었다”\r\n이건규, 강환국, 숙향 강력 추천!",0,"현명한 지표 투자.jpg",16920,"2023-01-13","아레미디어","현명한 지표 투자",4,0));
        bookRepo.save(new Book("박수준","투자자를 위한 업종별 투자 가이드",0,"대한민국 산업지도.jpg",19800,"2022-02-13","경이로움","대한민국 산업지도",4,0));
        bookRepo.save(new Book("김도영","네이버 브랜드 기획자가 안내하는\r\n지금 가장 특별한 브랜드들의 ‘관점과 태도’",0,"브랜드로부터 배웁니다.jpg",16200,"2021-03-23","위즈덤하우스","브랜드로부터 배웁니다",4,0));   
        bookRepo.save(new Book("세이노","재야의 명저 《세이노의 가르침》 2023년판 정식 출간!\r\n순자산 천억 원대 자산가, 세이노의 ‘요즘 생각’을 만나다",0,"세이노의 가르침.jpg",7200,"2023-03-02","데이원","세이노의 가르침",36,0));
        bookRepo.save(new Book("윤정은","우리는 가끔 시간을 되돌려 과거로 돌아가 후회됐던 일을 되돌리고 싶어한다. 그런데 과연 그 일을 지워버리는 게 현명한 선택일까? 그리고 그 기억을 지웠을 때 지금의 내가 있을 수 있을까?",0,"메리골드 마음 세탁소.jpg",15000,"2023-03-06","북로망스","메리골드 마음 세탁소",11,0));
        bookRepo.save(new Book("사이토 히토리","전대미문의 부자 사이토 히토리의 최고 베스트셀러!\r\n중고 서점에서 고가로 거래되던 전설의 절판 도서, 드디어 재출간!\r\n읽는 이의 삶을 바꾸는 놀라운 책\r\n펼치자마자 단숨에 읽게 되는 유쾌한 책\r\n당신을 행복한 부자로 만들어 줄 절대 성공의 법칙",0,"1퍼센트 부자의 법칙.jpg",17000,"2023-01-30","나비스쿨","1퍼센트 부자의 법칙",36,0));
        bookRepo.save(new Book("신카이 마코토","규슈의 조용한 마을에서 이모와 함께 살아가는 17살 소녀 스즈메. 어느 날 등굣길에 아름다운 청년과 스쳐간 스즈메는 “문을 찾고 있다”는 그의 뒤를 쫓아 산속 폐허에 들어선다. 그곳에서 스즈메가 발견한 것은 붕괴에서 빗겨난 듯 덩그러니 남겨진 낡고 하얀 문. 무언가에 이끌리듯 스즈메는 문을 향해 손을 뻗는데….",0,"스즈메의 문단속.jpg",13000,"2023-01-13","대원씨아이","스즈메의 문단속",39,0));
        bookRepo.save(new Book("게리 킬러","자 제이 파파산은 현재 켈러 윌리엄스 출판부의 부사장이자 렐릭 출판사의 대표이사다. 하퍼콜린스 편집자로 근무하는 동안 수많은 베스트셀러를 기획하고 편집한 바 있다. 게리 켈러와 함께 일한 10년 동안에는 켈러의 저서를 포함해 열 권의 베스트셀러를 공동 집필했다. 켈러 윌리엄스 국제대학교 교수진의 일원으로도 활발한 활동을 펼치고 있다.",0,"원씽.jpg",14000,"2013-08-30","비즈니스북스","원씽",36,0));
        bookRepo.save(new Book("팀 페리스","프린스턴 대학교에서 ‘기업가정신’을 강의하는 팀 페리스는 글로벌 CEO, 석학, 언론들에게서 ‘이 시대 가장 혁신적인 아이콘’으로 평가받고 있다. 그는 18세 이후 자신의 모든 것을 기록으로 남겨왔을 정도로 강박적인 노트 수집가다. 이 책 『타이탄의 도구들』은 그런 그가 모은 노트들 가운데 가장 빛나는 보물이라고 자신하는 책이다.",0,"타이탄의 도구들.jpg",18000,"2022-06-20","토네이도","타이탄의 도구들",36,0));
        bookRepo.save(new Book("소윤","2021년 최장 기간 동안 에세이 베스트 셀러 자리를 지킨 『작은 별이지만 빛나고 있어』가 20만 부를 기념하여 인기 일러스트레이터 제딧 작가와 협업한 리커버 에디션으로 돌아왔다.",0,"작은 별이지만 빛나고 있어.jpg",15000,"2021-09-29","북로망스","작은 별이지만 빛나고있어",11,0));
        bookRepo.save(new Book("정재호","40년 경력 개인 투자자들의 멘토 ‘부자아빠’ 정재호의 《주식 시세의 비밀》이 전면 개정판으로 출간되었다. ",0,"주식 시세의 비밀.jpg",22000,"2023-02-20","프런트페이지","주식 시세의 비밀",4,0));
        bookRepo.save(new Book("롭 무어","자본주의를 내 편으로 만드는 기술 “돈이 당신을 위해 일하게 하라!” 수많은 젊은 부자가 선택한 『레버리지』의 20만 부 돌파 블랙 에디션이 출간되었다. ",0,"레버리지.jpg",18000,"2023-02-15","다산북스","레버리지",4,0));
        bookRepo.save(new Book("무라세 다케시","2022년 출간 후 단숨에 외국 소설 분야 1위, 종합 베스트셀러에 오르며 수많은 독자의 마음을 울린 《세상의 마지막 기차역》. “읽는 내내 눈물이 펑펑 쏟아졌다",0,"세상의 마지막 기차역.jpg",14000,"2023-05-09","모모","세상의 마지막 기차역",39,0));
        bookRepo.save(new Book("히가시노 게이고","지유가오카에 있는 카페에서 여주인 하나즈카 야요이가 등에 칼이 꽂힌 채 사체로 발견된다. 경찰은 현장 상황 등으로 미루어 원한 등에 의한 면식범의 소행이라고 보고 수사에 들어간다.",0,"희망의끈.jpg",18800,"2023-03-15","재인","희망의 끈",39,0));
        bookRepo.save(new Book("손보미","엄선된 문학을 읽는 일, 그 강렬한 기쁨을 최대치로 끌어올리는 작가 손보미가 『우아한 밤과 고양이들』(문학과지성사, 2018) 이후 오 년 만에 신작 소설집 『사랑의 꿈』으로 돌아왔다.",0,"사랑의 꿈.jpg",16500,"2023-02-18","문학동네","사랑의 꿈",11,0));
        bookRepo.save(new Book("김초엽","이미 폭넓은 독자층을 형성하며 열렬한 사랑을 받고 있는 김초엽 작가는 더스트로 멸망한 이후의 세계를 첫 장편소설의 무대로 삼았다. ",0,"지구 끝의 온실.jpg",15000,"2021-08-18","자이언트북스","지구 끝의 온실",11,0));
        bookRepo.save(new Book("존 스트레키키","“당신은 왜 여기 있습니까?”\r\n눈앞의 세상이 바뀌는 질문",0,"세상 끝의 카페.jpg",16500,"2023-02-24","클레이하우스","세상 끝의 카페",39,0));
        bookRepo.save(new Book("안소린","16만 수험생들의 현역 공부 멘토 소린의 첫 책이다. 이 책은 그간 출간되었던 공부법 책과는 다르다단순히 공부법 노하우를 전수하는 게 아닌 고등학교 3학년, 입시에서 반드시 통하는 나만의 비밀 ",0,"서울대생의 비밀과외.jpg",18000,"2023-03-10","다산에듀","서울대생의 비밀과외",12,0));
		//2차입력
        bookRepo.save(new Book("칼 세이건","과학 교양서의 고전 코스모스. 이 책에서 저자는 우주의 탄생과 은하계의 진화, 태양의 삶과 죽음, 우주를 떠돌던 먼지가 의식 있는 생명이 되는 과정, 외계 생명의 존재 문제 등에 관한 내용을 수 백장의 사진과 일러스트를 곁들여 흥미롭게 설명한다.",0,"코스모스.jpg",19900,"2010-01-20","사이언스북스","코스모스",6,0));
        bookRepo.save(new Book("재레드 다이아몬드","이 책은 모든 인류가 수렵과 채집으로 살아가던 1만 3천 년 전 석기시대로 거슬러 올라간다. 저자는 제국, 지역, 문자, 농작물, 총의 기원뿐만 아니라 각 대륙의 인류 사회가 각기 다른 발전의 길을 걷게 된 원인을 설득력 있게 설명함으로써, 역사에 대한 인종주의적 이론의 허구를 벗겨낸다.",0,"총,균,쇠.jpg",28000,"2005-12-19","문학사상","총,균,쇠",16,0));
        bookRepo.save(new Book("김준성","ChatGPT의 원리 및 기본 사용법부터 블로그, 영상 제작, 업무 등에서 다양하게 활용하는 '진짜 활용법'을 담았습니다. 일상에서도 업무에서도 ChatGPT 그리고 다양한 AI 도구로 똑똑하게 일해보세요.",0,"진짜 챗GPT 활용법.jpg",18000,"2023-03-21","위키북스","진짜 챗GPT 활용법",29,0));
        bookRepo.save(new Book("손흥민","꿈 많은 춘천의 축구 소년에서 오늘의 손세이셔널이 되기까지 거침없이 질주해 온 손흥민이 처음 돌아본 자신의 이야기",0,"축구를 하며 생각한 것들.jpg",18000,"2020-08-21","브레인스토어","축구를 하며 생각한 것들",35,0));
        bookRepo.save(new Book("김주환","마음에도 근육이 있다. 몸의 근력처럼 마음근력도 체계적이고 반복적으로 훈련하면 강해진다. 베스트셀러 회복탄력성 출간 후 한층 더 깊이 마음근력 연구에 집중해온 김주환 교수는 신간 내면소통을 통해 마음근력을 키우는 가장 효과적인 방법을 밝혔다.",0,"내면소통.jpg",33000,"2023-02-27","인플루엔셜","내면소통",43,0));
        
        
        
//		LocalDate nowDate = LocalDate.now();
//		String[] testImages = {"뉴진스.jpeg","뉴진스페페1.png","다니엘.jpeg","다니엘2.jpeg","민지.jpeg",
//								"하니.jpeg","하니2.jpeg","해린.jpeg","해린2.jpeg","혜인.jpeg"};
//		for(int i =0; i<testImages.length; i++) {
//			Book book = new Book();
//			book.setTitle(testImages[i].substring(0,testImages[i].length()-5));
//			book.setAuthor("author");
//			book.setPublisher("publisher");
//			book.setPublicationDate(nowDate);
//			book.setPrice(29000 + i*100);
//			book.setDescription("테스트 코드에서 입력 되었습니다.");
//			book.setCategory(new Category(3));
//			book.setImage(testImages[i]);
//			bookRepo.save(book);
//		}

	}
	
//	@Test
//	public void saveNewCart() {
//		Book book = new Book(1);
//		User user = new User(1);
//		for(int i =0; i < 3; i++) {
//			Cart cart = new Cart();
//			cart.setBookQuantity(1);
//			cart.setBook(book);
//			cart.setUser(user);
//			cartRepo.save(cart);
//		}
//	}
	
	@Test
	public void addStockBook() {
		//책 재고 추가하기
		List<Book> book = (List<Book>) bookRepo.findAll();
		List<Branches> branch = (List<Branches>) branchRepo.findAll();
		
		for (int i= 0; i < branch.size(); i ++) {
			for(int j = 0; j < book.size(); j++) {
				BooksBranch booksBranch = new BooksBranch();
				booksBranch.setBranches(branch.get(i));
				booksBranch.setBook(book.get(j));
				booksBranch.setQuantity(10);
				//booksBranch.setStatus("new"); // 정상작동 확인후 condition컬럼은 지울것;
				bookBranchRepo.save(booksBranch);
			}
		}
	}
	
	@Test
	public void addApiKey() {
		String OpenAIApiKeyValue = "your_api_key_value";
		apiRepo.save(new ApiKey("OpenAI",OpenAIApiKeyValue));
	}


}