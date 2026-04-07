# 추지호 | 풀스택 개발자 포트폴리오

> **컨셉**: 안정적인 시스템 운영과 성능 최적화에 탁월한 풀스택 개발자  
> **최종 업데이트**: 2026-04-02 (v3 — 이미지 기반 실제 이슈 100건+ 전량 반영 + Java 자동화 툴 상세화)

---

## ✅ 현재 완성된 기능

### 🔢 Numbers That Matter (정량 성과)
- **누적 커밋 & 이슈 해결 500건+** (이미지 확인 100+건 포함)
- **장애 재발 Zero** (RCA 기반 리팩토링)
- **성능 2x 향상** (문자열 처리·메모리 최적화)
- **ERP 연동 양식 10+건** (의료·행정·금융 도메인)
- **연간 고도화 2건+** (전자결재 연동 단독 수행)
- **운영 고객사 유형 5+** (공공·금융·기업·대학·의료)
- **20,000건+** 대량 전자문서 자동 검증 처리

### 🗂️ Technical Portfolio (7개 STAR 사례)
1. **SocketException FD 증가 → 서비스 접속 불가 해결** (Java InputStream 미종료 누수)
2. **Mail Queue 무한 루프 → 메일 서버 안정화** (라이브러리 패치 + 방어 로직)
3. **DB 인덱스 튜닝 → 결재 속도 2x 향상** (서브쿼리 limit + join 방식 개선)
4. **Bulk Insert 전환 → 연동 문서 처리 성능 개선** (반복 Insert → Bulk)
5. **log 파일 누적 → 디스크 풀 예방** (cron 자동화 스크립트)
6. **Mail Queue 무한 루프 패치** (보내는사람 수신함 꽉 참 바운스 루프)
7. **[NEW] 20,000건 전자문서 HWP 시행일자 DB 정합성 자동 검증 툴** (Java 단독 개발)

### 🔍 Technical Case Study (심층 RCA 3종)
- **FD Leak**: SocketException 근본 원인 분석 → 코드 패치
- **Mail Loop**: 무한 루프 제거 → 큐 정상화
- **DB Tuning**: 인덱스 힌트 + 쿼리 구조 개선

### 🛠️ Skills (탭 분류)
- **Backend**: Java (Advanced·88%), Spring Framework (Advanced·85%), Maven (Intermediate+·75%)
- **Database**: Oracle, MySQL, MariaDB + 쿼리 튜닝·인덱스 최적화
- **Infra/DevOps**: Linux, Tomcat, cron, Git
- **Frontend**: JSP, JavaScript, HTML/CSS, jQuery

### 🔗 ERP 연동 고도화 (단독 수행 섹션)
- 5단계 연동 라이프사이클 시각화
- Before/After 성능 비교 바
- 도메인별 연동 양식 10건+ 목록 (의과대·행정·금융)

### 📊 Performance Charts (Chart.js 3종)
- 연도별 재발 장애 건수 라인 차트
- 처리 유형별 도넛 차트
- 도메인별 이슈 분포 바 차트

### 📋 실제 커밋 & 이슈 이력 (REAL WORK LOG)
- 이미지에서 추출한 실제 이슈 **100건 이상** 전량 수록
- 도메인별 이슈 통계: 전자결재(45%), ERP연동(20%), 자기점검(10%), 자원·설문·일정(8%), 성능·보안·인프라(10%), 자동화·메일·기타(7%)
- 필터: All / 오류 / 개선 / 개발 / 성능
- 더보기/접기 토글
- 이슈 유형별 뱃지 + 도메인 태그

---

## 🌐 진입 URI 요약

| 섹션 | 앵커 |
|------|------|
| Intro (Hero) | `/#intro` |
| Numbers | `/#metrics` |
| Skills | `/#skills` |
| Charts | `/#charts` |
| Portfolio | `/#portfolio` |
| Case Study | `/#casestudy` |
| ERP 연동 | `/#erp` |
| 실제 이슈 이력 | `/#commitlog` |
| Contact | `/#contact` |

---

## 📁 파일 구조

```
index.html          # 메인 단일 페이지 (SPA)
css/
  style.css         # 다크 테마·반응형·애니메이션·컴포넌트 스타일
js/
  main.js           # 전체 인터랙티브 로직
README.md
```

---

## 🎯 핵심 인터랙티브 기능

- Hero 3가지 헤드라인 자동 로테이션
- 파티클 배경 애니메이션
- 숫자 카운트업 (뷰포트 진입 시)
- Scroll Reveal (stagger 딜레이)
- 3D 틸트 호버 (portfolio-card, metric-card)
- 코드 스니펫 클립보드 복사 버튼
- 커밋 피드 타입 필터 + 더보기/접기
- Skills 탭 전환
- Case Study 탭 전환 (FD/Mail/DB)
- ERP 성능 바 애니메이션 (IntersectionObserver)

---

## 💡 데이터 모델 & 이슈 분류

| 타입 | 설명 | 뱃지 색상 |
|------|------|----------|
| 오류 | Bug·Exception 수정 | 빨강 |
| 개선 | 기능 개선·보안·UX | 보라 |
| 개발 | 신규 기능 개발 | 파랑 |
| 성능 | DB 튜닝·속도 개선 | 주황 |
| 문의 | VOC·운영 문의 처리 | 회색 |

---

## 🚧 미구현 / 권장 다음 단계

- [ ] 실제 GitHub 링크 연결 (현재 '#')
- [ ] 블로그/기술글 외부 링크 연결
- [ ] 연락처 폼 실제 이메일 전송 연동 (Formspree 등)
- [ ] 다국어(영문) 버전 추가
- [ ] PWA(오프라인 지원) 추가
