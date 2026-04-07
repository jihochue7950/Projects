/*
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;

@DataJpaTest(showSql = false)
//데이터 베이스의 데이터가 더 우세 하니 바꾸지 말아라
@AutoConfigureTestDatabase(replace = Replace.NONE)
//에디터에서 데이터베이스로 데이터 넣기가 가능해짐
@Rollback(false) 
public class OldCategoryTest {
	
	@Autowired
	private CategoryRepository caRepo;
	
	//@Test
	void CreateCategory() {
	
	Category category000 = new Category("000", "총류"); caRepo.save(category000);
	Category category100 = new Category("100", "철학"); caRepo.save(category100);
	Category category200 = new Category("200", "종교"); caRepo.save(category200);
	Category category300 = new Category("300", "사회과학"); caRepo.save(category300);
	Category category400 = new Category("400", "자연과학"); caRepo.save(category400);
	Category category500 = new Category("500", "기술과학"); caRepo.save(category500);
	Category category600 = new Category("600", "예술"); caRepo.save(category600);
	Category category700 = new Category("700", "언어"); caRepo.save(category700);
	Category category800 = new Category("800", "문학"); caRepo.save(category800);
	Category category900 = new Category("900", "역사"); caRepo.save(category900);
	
	caRepo.save(new Category("010", "도서학,서지학", category000));
	caRepo.save(new Category("020", "문헌정보학", category000));
	caRepo.save(new Category("030", "백과사전", category000));
	caRepo.save(new Category("040", "강연집,수필집,연설문집", category000));
	caRepo.save(new Category("050", "일반연속간행물", category000));
	caRepo.save(new Category("060", "일반학회,단체,협회,기관", category000));
	caRepo.save(new Category("070", "신문,언론,저널리즘", category000));
	caRepo.save(new Category("080", "일반전집,총서", category000));
	caRepo.save(new Category("090", "향토자료", category000));
	
	caRepo.save(new Category("110", "형이상학", category100));
	caRepo.save(new Category("120", "인식론,인과론,인간학", category100));
	caRepo.save(new Category("130", "철학의 세계", category100));
	caRepo.save(new Category("140", "경학", category100));
	caRepo.save(new Category("150", "동양철학,사상", category100));
	caRepo.save(new Category("160", "서양철학", category100));
	caRepo.save(new Category("170", "논리학", category100));
	caRepo.save(new Category("180", "심리학", category100));
	caRepo.save(new Category("190", "윤리학,도덕철학", category100));
	
	caRepo.save(new Category("210", "비교종교", category200));
	caRepo.save(new Category("220", "불교", category200));
	caRepo.save(new Category("230", "기독교", category200));
	caRepo.save(new Category("240", "도교", category200));
	caRepo.save(new Category("250", "천도교", category200));
	caRepo.save(new Category("260", "신도", category200));
	caRepo.save(new Category("270", "힌두교,브라만교", category200));
	caRepo.save(new Category("280", "이슬람교(회교)", category200));
	caRepo.save(new Category("290", "기타 제종교", category200));
	
	caRepo.save(new Category("310", "통계학", category300));
	caRepo.save(new Category("320", "경제학", category300));
	caRepo.save(new Category("330", "사회학,사회문제", category300));
	caRepo.save(new Category("340", "정치학", category300));
	caRepo.save(new Category("350", "행정학", category300));
	caRepo.save(new Category("360", "법학", category300));
	caRepo.save(new Category("370", "교육학", category300));
	caRepo.save(new Category("380", "풍속,예절,민속학", category300));
	caRepo.save(new Category("390", "국방,군사학", category300));
	
	caRepo.save(new Category("410", "수학", category400));
	caRepo.save(new Category("420", "물리학", category400));
	caRepo.save(new Category("430", "화학", category400));
	caRepo.save(new Category("440", "천문학", category400));
	caRepo.save(new Category("450", "지학", category400));
	caRepo.save(new Category("460", "광물학", category400));
	caRepo.save(new Category("470", "생명과학", category400));
	caRepo.save(new Category("480", "식물학", category400));
	caRepo.save(new Category("490", "동물학", category400));
	
	caRepo.save(new Category("510", "의학", category500));
	caRepo.save(new Category("520", "농업,농학", category500));
	caRepo.save(new Category("530", "공학,공업일반,토목공학,환경공학", category500));
	caRepo.save(new Category("540", "건축광학", category500));
	caRepo.save(new Category("550", "기계공학", category500));
	caRepo.save(new Category("560", "전기공학,전자공학", category500));
	caRepo.save(new Category("570", "화학공학", category500));
	caRepo.save(new Category("580", "제조업", category500));
	caRepo.save(new Category("590", "생활과학", category500));
	
	caRepo.save(new Category("610", "건축물", category600));
	caRepo.save(new Category("620", "조각,조형예술", category600));
	caRepo.save(new Category("630", "공예,장식미술", category600));
	caRepo.save(new Category("640", "서예", category600));
	caRepo.save(new Category("650", "회화,도화", category600));
	caRepo.save(new Category("660", "사진예술", category600));
	caRepo.save(new Category("670", "음악", category600));
	caRepo.save(new Category("680", "공연예술,매체예술", category600));
	caRepo.save(new Category("690", "오락,스포츠", category600));
	
	caRepo.save(new Category("710", "한국어", category700));
	caRepo.save(new Category("720", "중국어", category700));
	caRepo.save(new Category("730", "일본어,기타아시아제어", category700));
	caRepo.save(new Category("740", "영어", category700));
	caRepo.save(new Category("750", "독일어", category700));
	caRepo.save(new Category("760", "프랑스어", category700));
	caRepo.save(new Category("770", "스페인어,포르투갈어", category700));
	caRepo.save(new Category("780", "이탈리아어", category700));
	caRepo.save(new Category("790", "기타제어", category700));
	
    caRepo.save(new Category("810","한국문학",category800));
    caRepo.save(new Category("820","중국문학",category800));
    caRepo.save(new Category("830","일본문학,기타아시아문학",category800));
    caRepo.save(new Category("840","영미문학",category800));
    caRepo.save(new Category("850","독일문학",category800));
    caRepo.save(new Category("860","프랑스문학",category800));
    caRepo.save(new Category("870","스페인,포르투갈문학",category800));
    caRepo.save(new Category("880","이탈리아문학",category800));
    caRepo.save(new Category("890","기타제문학",category800));
    
    caRepo.save(new Category("910","아시아",category900));
    caRepo.save(new Category("920","유럽",category900));
    caRepo.save(new Category("930","아프리카",category900));
    caRepo.save(new Category("940","북아메리카",category900));
    caRepo.save(new Category("950","남아메리카",category900));
    caRepo.save(new Category("960","오세아니라",category900));
    caRepo.save(new Category("970","양극지방",category900));
    caRepo.save(new Category("980","지리",category900));
    caRepo.save(new Category("990","전기",category900));
    

	}

}
*/
