package Airplane_Project;


import Airplane_Project.info.Mem_info;
import Airplane_Project.info.non_user;
import org.sqlite.core.DB;

import javax.naming.BinaryRefAddr;
import javax.naming.Name;
import javax.sound.midi.Patch;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

class Operation {
    public static final String DB_NAME = "airplane_prj.db";
    public static final String CONNECTION_URL = "jdbc:sqlite:C:\\Users\\402-018\\airplane_prj.db";

    public static final String ORDER_BY_ASC = "ASC";
    public static final String ORDER_BY_DESC = "DESC";
    public static final String ORDER_BY_NONE = "NONE";

    public static final String TABLE_PER_INFO = "per_info";
    public static final String TABLE_PLANE_SCH = "plane_sch";
    public static final String TABLE_SEAT_TABLE = "seat_table";


    public static final String COLUMN_PER_INFO_PER_NUM = "per_num";
    public static final String COLUMN_PER_INFO_NAME = "name";
    public static final String COLUMN_PER_INFO_ID = "ID";
    public static final String COLUMN_PER_INFO_PW = "PW";
    public static final String COLUMN_PER_INFO_BIRTHDATE = "birthdate";
    public static final String COLUMN_PER_INFO_GENDER = "gender";
    public static final String COLUMN_PER_INFO_NATIONALITY = "nationality";
    public static final String COLUMN_PER_INFO_PHONE = "phone";
    public static final String COLUMN_PER_INFO_RES_CHECK = "res_check";


    public static final String COLUMN_PLANE_SCH_PS_NUM = "ps_num";
    public static final String COLUMN_PLANE_SCH_PLANE_NAME = "plane_name";
    public static final String COLUMN_PLANE_SCH_DATE = "date";
    public static final String COLUMN_PLANE_SCH_DEP_CITY = "dep_city";
    public static final String COLUMN_PLANE_SCH_ARRI_TIME = "arri_time";
    public static final String COLUMN_PLANE_SCH_ARRI_CITY = "arri_city";
    public static final String COLUMN_PLANE_SCH_FLIGHT_TIME = "flight_time";
    public static final String COLUMN_PLANE_SCH_DEP_TIME = "dep_time";


    public static final String COLUMN_SEAT_TABLE_SEAT_NUM = "seat_num";
    public static final String COLUMN_SEAT_TABLE_SEAT_CLASS = "seat_class";
    public static final String COLUMN_SEAT_TABLE_SEAT_PRICE = "seat_price";
    public static final String COLUMN_SEAT_TABLE_SEAT_NAME = "seat_name";
    public static final String COLUMN_SEAT_TABLE_PS_KEY = "ps_key";
    public static final String COLUMN_SEAT_TABLE_RESERVATION_ID = "reservation_id";

    public static final String
            RESERVATION_INQUIRY_QUERY = "SELECT " + COLUMN_PER_INFO_NAME + ", " + COLUMN_PER_INFO_ID + ", " +
            "IFNULL(MAX(" + COLUMN_PER_INFO_PW + ") ,0) AS " + COLUMN_PER_INFO_PW +
            ", " + COLUMN_PER_INFO_RES_CHECK +
            " from " + TABLE_PER_INFO;

    private static final String RESERVATION_CANCEL = "update " + TABLE_PER_INFO + " set " + COLUMN_PER_INFO_RES_CHECK;

    private static final String RESERVATION_ID_CANCEL = "update " + TABLE_SEAT_TABLE
            + " set " + COLUMN_SEAT_TABLE_RESERVATION_ID;
    public static final String SELECT_NAME_FROM_PER_INFO = "SELECT name FROM per_info";
    public static final String SELECT_ID_PW_FROM_PER_INFO = "SELECT ID , PW  FROM per_info";
    public static final String SQL_RESERVATION_INFORMATION = "SELECT plane_name , date , dep_city , dep_time , " +
            "arri_time , arri_city , flight_time , seat_class , seat_name FROM " +
            "seat_table JOIN plane_sch ON seat_table.ps_key = plane_sch.ps_num WHERE reservation_ID = ";
    public static final String SQL_RESERVATION_INFORMATION_RETURN = "SELECT plane_name , date , dep_city , dep_time , " +
            "arri_time , arri_city , flight_time , seat_class , seat_name FROM " +
            "seat_table_return JOIN plane_sch_return ON seat_table_return.ps_key = plane_sch_return.ps_num WHERE reservation_ID = ";
//------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------
    static String dep,arri;
    private Connection conn;
    Scanner scanner;

    void Open(){
        try {
            conn = DriverManager.getConnection(CONNECTION_URL);
            System.out.println("CONNECTION OPEN");

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    void Close(){
        try {
            if (conn!=null){
                conn.close();
                System.out.println("CONNECTION CLOSE");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
//    =================================================================================================================
//    =================================================================================================================
    void frontWindow() {
        System.out.println("JH 여행사");
        System.out.println("1.항공권 예약하기 ");
        System.out.println("2.항공권 조회하기 ");
        System.out.println("3.항공권 취소하기 ");
        System.out.println("4.항공 스케줄 확인하기");
        Integer num =0;

        try {
            System.out.println("원하시는 항목의 숫자를 입력해주세요.");
            System.out.format("입력:");
            scanner = new Scanner(System.in);
            num = scanner.nextInt();
            System.out.format("%d번 항목을 선택하셨습니다.\n해당항목으로 이동합니다.\n", num);
            if (num <= 0 || num >= 5){
                System.out.println("잘못 입력하셨습니다.\n메뉴에 맞는 번호를 입력해주세요.");
                frontWindow();
            }
        } catch (InputMismatchException e){
            System.out.println("잘못 입력하셨습니다.\n숫자를 입력해주세요.");
            frontWindow();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        switch (num) {
            case 1:
                FightSelect();
                break;
            case 2:
                checkReservation ();
                break;
            case 3:
                input_num(num);
                break;
            case 4:
                break;

        }
    }
//    =================================================================================================================
//    ======================(프론트 창)=================================================================================
//===============================(비회원 로그인 및 항공권 예매)============================================================
    public void Insert_nonUser (){
        String regName = "^[a-zA-Z]*$";
        String reg = "^[0-9]*$";
        String regPhone = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        String regBirthdate = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
        String regNationality = "^[a-zA-Z]*$";
        String regGender = "^[a-zA-Z]*$";

        String Name;
        String Phone_Number;
        String Birthdate;
        String Gender;
        String Nationality;
        boolean w;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("이름을 입력해주세요 EX) EDWARD");
            Name = sc.next(); // 위에 Name same
            w = Pattern.matches(regName, Name);
            if (w == true) {

            } else {
                System.out.println("영문자만 입력해주세요");
            }

        } while (!w);

        boolean e;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("핸드폰 번호를 입력해주세요 EX) 010-1234-1234");
            Phone_Number = sc.next();
            e = Pattern.matches(regPhone, Phone_Number);
            if (e == true) {

            } else {
                System.out.println("조건에 충족 하도록 만들어주세요");
            }
        } while (!e);

        boolean t;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("생일을 입력해주세요 EX) 2000-12-15");
            Birthdate = sc.next();
            t = Pattern.matches(regBirthdate, Birthdate);
            if (t == true) {

            } else {
                System.out.println("조건에 충족 하도록 만들어주세요");
            }
        } while (!t);

        boolean s;
        do {
            System.out.println("성별을 입력해주세요 EX) male / female");
            Scanner sc = new Scanner(System.in);
            Gender = sc.next();
            s = Pattern.matches(regGender, Gender);
            if (s == true && Gender.equals("male") || Gender.equals("female")) {

            } else {
                System.out.println("조건에 총족 하도록 만들어주세요");
            }
        } while (!(s == true && Gender.equals("male") || Gender.equals("female")));

        boolean q;
        do {
            System.out.println("국적을 적어주세요 EX) KOREA");
            Scanner sc = new Scanner(System.in);
            Nationality = sc.next();
            q = Pattern.matches(regNationality, Nationality);
            if (q == true) {

            } else {
                System.out.println("숫자 입력하지마");
            }
        } while (!q);

        try{
            StringBuilder stringBuilder = new StringBuilder("INSERT INTO non_user (name,birthdate,gender,nationality,phone) VALUES");
            stringBuilder.append("(");
            stringBuilder.append("'" + Name + "'" + ",");
            stringBuilder.append("'" + Birthdate + "',");
            stringBuilder.append("'" + Gender + "',");
            stringBuilder.append("'" + Nationality + "',");
            stringBuilder.append("'" + Phone_Number + "'");
            stringBuilder.append(")");
            Statement statement = conn.createStatement();
            statement.execute(stringBuilder.toString());

            System.out.println("비회원 고객으로 로그인 되었습니다");

        }catch (SQLException EE){
            System.out.println(EE.getMessage());

        }
        FlightSelect_nonUser();
    }

    void FlightSelect_nonUser(){
        System.out.println("원하는 여행지를 입력해주세요.");
        try(Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT(dep_city) FROM plane_sch");
            System.out.println("---출발지 리스트---");
            System.out.println("-----------------");
            while (resultSet.next()){
                System.out.format("|    %s      |\n",resultSet.getString("dep_city"));
            }
            System.out.println("-----------------");
            System.out.format("출발지 입력:");
            scanner = new Scanner(System.in);
            dep = scanner.nextLine();
            resultSet = statement.executeQuery("SELECT DISTINCT(arri_city) FROM plane_sch");
            System.out.println("-----------------");
            System.out.println("---도착지 리스트---");
            System.out.println("-----------------");
            while (resultSet.next()){
                System.out.format("| %s |\n",resultSet.getString("arri_city"));
            }
            System.out.format("도착지 입력:");
            scanner = new Scanner(System.in);
            arri = scanner.nextLine();


            //SELECT *,ifnull(max(dep_city),0) FROM plane_sch WHERE dep_city="ICN" AND arri_city ="HANEDA"
            resultSet =statement.executeQuery("SELECT *,ifNull(max(dep_city),0) FROM plane_sch WHERE dep_city=\""+dep+"\" AND arri_city =\""+arri+"\"");
            if (resultSet.getString("ifNull(max(dep_city),0)").equals("0")) {
                System.out.println("잘못 입력하셨습니다. \n다시 입력해주세요.");
                FlightSelect_nonUser();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        FlightSchedule_nonUser();
    }


    void FlightSchedule_nonUser(){
        Integer nums ;
        Integer nums2;
        ResultSet resultSet1,resultSet2;
        try(Statement statement = conn.createStatement()) {
            resultSet1 =statement.executeQuery("SELECT * FROM plane_sch WHERE dep_city=\""+dep+"\" AND arri_city =\""+arri+"\"");
            System.out.println("다음 항공노선 중 원하는 항공노선의 번호를 입력해주세요.");
            while (resultSet1.next()) {
                System.out.format("%d. 항공기이름:%s 출발지:%s 출발시간:%s 도착시간:%s 도착지:%s 예상비행시간:%s \n", resultSet1.getInt("ps_num"), resultSet1.getString("plane_name"),
                        resultSet1.getString("dep_city"), resultSet1.getString("dep_time"), resultSet1.getString("arri_time"),
                        resultSet1.getString("arri_city"), resultSet1.getString("flight_time"));
            }
            System.out.format("입력:");
            scanner = new Scanner(System.in);
            nums = scanner.nextInt();
            resultSet2 =statement.executeQuery("SELECT Count(ps_num) AS ps_c FROM plane_sch WHERE dep_city=\""+dep+"\" AND arri_city =\""+arri+"\"");
            nums2 = resultSet2.getInt("ps_c")+1;


            if (0<nums && nums2>nums) {
                seatTable_nonUser(nums);
            } else{
                System.out.println("잘못입력하셨습니다.\n다시입력해주세요.");
                FlightSchedule_nonUser();
            }
        }catch (InputMismatchException e){
            System.out.println("잘못입력하셨습니다.\n다시입력해주세요.");
            FlightSchedule_nonUser();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
    void seatTable_nonUser(Integer nums){
        int i=0;
        try(Statement statement = conn.createStatement()) {
            ResultSet resultSet2 = statement.executeQuery("SELECT *,ifNULL(reservation_ID,0) AS rsv FROM seat_table WHERE ps_key ="+nums+" AND rsv = 0");
            while (resultSet2.next()){
                i++;
                System.out.format(i+". 좌석등급:%s 가격:%s 자리:%s \n", resultSet2.getString("seat_class"),resultSet2.getString("seat_price"),
                        resultSet2.getString("seat_name"));

            }
            System.out.format("입력:");
            scanner = new Scanner(System.in);
            int numbers = scanner.nextInt();
            resultSet2 =statement.executeQuery("SELECT Count(seat_num) FROM seat_table WHERE ps_key = "+nums);
            if (0>=numbers||resultSet2.getInt("Count(seat_num)")<numbers) {
                System.out.println("잘못입력하셨습니다.\n다시입력해주세요.");
                seatTable_nonUser(nums);
            }else {
                System.out.println("다음단계로 이동합니다.");
                System.out.println(numbers+" "+ nums);
                Update_nonUser(numbers,nums);
            }
        }catch (InputMismatchException e){
            System.out.println("잘못 입력하셨습니다.\n숫자를 입력해주세요.");
            seatTable_nonUser(nums);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
    void Update_nonUser( int numbers , int nums ){
        System.out.println(numbers + " " + nums);
        String regName = "^[a-zA-Z]*$";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM non_user");
            ArrayList<String> Non_users = new ArrayList<>();
            while (rs.next()){
                non_user non_user1 = new non_user();
                non_user1.setName(rs.getString("name"));
                Non_users.add(non_user1.getName());
            }
            String Name;
            boolean rr;
            do{
                System.out.println("비회원님의 성함을 다시 한번 입력해주세요");
                Scanner scanner1 = new Scanner(System.in);
                Name = scanner1.nextLine();
                rr = Pattern.matches(regName , Name);
                if (rr == true && Non_users.contains(Name)){
                    System.out.println("확인 되었습니다 감사합니다");
                }else if(rr==true && !Non_users.contains(Name)){
                    System.out.println("성함이 일치하지 않습니다 다시 입력해주세요");
                }else {
                    System.out.println("영문자로만 입력해주세요");
                }
            }while (!(rr == true && Non_users.contains(Name)));

            ArrayList<Integer> non_users;
            int Reservation_num;
            do {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT IFNULL(MAX(reser_num),0)AS 'reser_num' FROM non_user");
                non_users = new ArrayList<>();
                while (resultSet.next()) {
                    non_user non_user = new non_user();
                    non_user.setReser_num(resultSet.getInt("reser_num"));
                    non_users.add(non_user.getReser_num());
                }
                System.out.println(non_users);
                Reservation_num = (int) Math.round(Math.random() * 100000000 );
                System.out.println("예약번호 : " + Reservation_num);
                if (!(non_users.contains(Reservation_num))){
                }
            } while (non_users.contains(Reservation_num));
            Statement statement = conn.createStatement();
            statement.execute("UPDATE non_user SET reser_num = \"" + Reservation_num + "\" WHERE name = " + "'"+Name+"'");
            statement.execute("UPDATE seat_table SET reservation_ID = \"" + Reservation_num + "\" WHERE seat_name = \"A" + numbers + "\" AND ps_key = " +nums);
        }catch (SQLException EEE){
            System.out.println(EEE.getMessage());
            EEE.printStackTrace();
        }

    }
//======================================================================================================================
//    ====================================(회원 항공원 예메)===============================================================
    void FightSelect(){

        System.out.println("원하는 여행지를 입력해주세요. FlightSelect");
        try(Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT(dep_city) FROM plane_sch");
            System.out.println("---출발지 리스트---");
            System.out.println("-----------------");
            while (resultSet.next()){
                System.out.format("|    %s      |\n",resultSet.getString("dep_city"));
            }
            System.out.println("-----------------");
            System.out.format("출발지 입력:");
            scanner = new Scanner(System.in);
            dep = scanner.nextLine();
            resultSet = statement.executeQuery("SELECT DISTINCT(arri_city) FROM plane_sch");
            System.out.println("-----------------");
            System.out.println("---도착지 리스트---");
            System.out.println("-----------------");
            while (resultSet.next()){
                System.out.format("| %s |\n",resultSet.getString("arri_city"));
            }
            System.out.format("도착지 입력:");
            scanner = new Scanner(System.in);
            arri = scanner.nextLine();

            //SELECT *,ifnull(max(dep_city),0) FROM plane_sch WHERE dep_city="ICN" AND arri_city ="HANEDA"
            resultSet =statement.executeQuery("SELECT *,ifNull(max(dep_city),0) FROM plane_sch WHERE dep_city=\""+dep+"\" AND arri_city =\""+arri+"\"");
            if (resultSet.getString("ifNull(max(dep_city),0)").equals("0")) {
                System.out.println("잘못 입력하셨습니다. \n다시 입력해주세요.");
                FightSelect();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        FlightSchedule();
    }


//
    void FlightSchedule(){
        Integer num =0 ;
        Integer num2;
        ResultSet resultSet1,resultSet2;
        try(Statement statement = conn.createStatement()) {
            resultSet1 =statement.executeQuery("SELECT * FROM plane_sch WHERE dep_city=\""+dep+"\" AND arri_city =\""+arri+"\"");
            System.out.println("다음 항공노선 중 원하는 항공노선의 번호를 입력해주세요.");
            while (resultSet1.next()) {
                System.out.format("%d. 항공기이름:%s 출발지:%s 출발시간:%s 도착시간:%s 도착지:%s 예상비행시간:%s \n", resultSet1.getInt("ps_num"), resultSet1.getString("plane_name"),
                        resultSet1.getString("dep_city"), resultSet1.getString("dep_time"), resultSet1.getString("arri_time"),
                        resultSet1.getString("arri_city"), resultSet1.getString("flight_time"));
            }
            System.out.format("입력:");
            scanner = new Scanner(System.in);
            num = scanner.nextInt();
            resultSet2 =statement.executeQuery("SELECT Count(ps_num) AS ps_c FROM plane_sch WHERE dep_city=\""+dep+"\" AND arri_city =\""+arri+"\"");
            num2 = resultSet2.getInt("ps_c")+1;


            if (0<num && num2>num) {
                SeatTable(num);
            } else{
                System.out.println("잘못입력하셨습니다.\n다시입력해주세요.");
                FlightSchedule();
            }
        }catch (InputMismatchException e){
            System.out.println("잘못입력하셨습니다.\n다시입력해주세요.");
            FlightSchedule();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    void SeatTable(Integer num){
        int i=0;
        try(Statement statement = conn.createStatement()) {
            ResultSet resultSet2 = statement.executeQuery("SELECT *,ifNULL(reservation_ID,0) AS rsv FROM seat_table WHERE ps_key ="+num+" AND rsv = 0");
            while (resultSet2.next()){
                i++;
                System.out.format(i+". 좌석등급:%s 가격:%s 자리:%s \n", resultSet2.getString("seat_class"),resultSet2.getString("seat_price"),
                        resultSet2.getString("seat_name"));

            }
            System.out.format("입력:");
            scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            resultSet2 =statement.executeQuery("SELECT Count(seat_num) FROM seat_table WHERE ps_key = "+num);
            if (0>=number||resultSet2.getInt("Count(seat_num)")<number) {
                System.out.println("잘못입력하셨습니다.\n다시입력해주세요.");
                SeatTable(num);
            }else {
                    System.out.println("다음단계로 이동합니다.");
                    MemInfo(number,num);
                }
        }catch (InputMismatchException e){
            System.out.println("잘못 입력하셨습니다.\n숫자를 입력해주세요.");
            SeatTable(num);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    void MemInfo(int number,int num) {
        ResultSet resultSet3;
        Scanner scanner;
        Mem_info mem = new Mem_info();
        String id;
        String pw;
        String app;
        try (Statement statement = conn.createStatement()) {
            System.out.println("계정 정보를 입력해주세요.");
            System.out.format("아이디:");
            scanner = new Scanner(System.in);
            id = scanner.nextLine();
            System.out.format("비밀번호:");
            scanner = new Scanner(System.in);
            pw = scanner.nextLine();
            resultSet3 = statement.executeQuery("SELECT ID,PW FROM per_info WHERE ID=\"" + id + "\" AND PW = \"" + pw + "\"");
            mem.setID(resultSet3.getString("ID"));
            mem.setPW(resultSet3.getString("PW"));
            if (id.equals(mem.getID())&&pw.equals(mem.getPW())){
                System.out.println("회원이 확인 되었습니다.");
            }
            boolean sk;
            do {
                System.out.format("예매 하시겠습니까?(Y/N)\n");
                System.out.format("입력:");
                scanner = new Scanner(System.in);
                app = scanner.nextLine();
                String aas = app.toUpperCase();
                if (aas.equals("YES") || aas.equals("Y")) {
                    statement.execute("UPDATE seat_table SET reservation_ID = \"" + mem.getID() + "\" WHERE seat_name = \"A" + number + "\" AND ps_key = " + num);
                    statement.execute("UPDATE per_info SET res_check = \"YES\" WHERE ID = \"" + mem.getID() + "\" AND PW = \"" + mem.getPW() + "\"");
                    System.out.println("예매가 완료 되었습니다.\n감사합니다.");
                    System.out.println("사용을 종료합니다.");
                    sk = true;
                } else if (aas.equals("NO") || aas.equals("N")) {
                    System.out.println("예매를 취소합니다.");
                    System.out.println("사용을 종료합니다.");
                    sk = true;
                } else {
                    System.out.printf("잘못 입력하셨습니다.\n");
                    sk = false;
                }
            }while (sk!=true);
        } catch (SQLException e) {
            System.out.printf("입력하신 회원의 정보는 없습니다. \n다시 입력해주세요.");
            MemInfo(number,num);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
//    =================================================================================================================
//    ==========(항공원 예매 취소)========================================================================================
    public void input_num(Integer num) {
        try {
            switch (num) {
                case 3:
                    System.out.println("3.항공권 예매취소를 선택하셨습니다");
                    Scanner sc1 = new Scanner(System.in);
                    String cn;
                    String id;
                    String pw;

                    int cnum;

                    test:
                    while (true) {
                        try {
                            System.out.print("\n예매자의 성함을 입력해주세요 : ");
                            cn = sc1.nextLine();
                            System.out.print("\nID를 입력해주세요 : ");
                            id = sc1.nextLine();
                            System.out.print("\nPASSWORD를 입력해주세요 : ");
                            pw = sc1.next();
                            sc1.nextLine();

                            ArrayList<Mem_info> cus_info = input_cusInfo(cn, id, pw);
                            for (Mem_info info : cus_info) {
                                if (Integer.parseInt(info.getPW()) != 0) {

                                    System.out.println();
                                    System.out.println("예약이 확인되었습니다.\n");
                                    System.out.println("[예매자 성함] : " + info.getName() + " [ID] : " + info.getID()
                                            + " [PW] : " + info.getPW());
                                    if (info.getRes_check().equals("YES")) {

                                        while (true) {

                                            System.out.println("\n정말 예매를 취소하시겠습니까?\n 1. 예     2. 아니오");
                                            cnum = sc1.nextInt();
                                            if (cnum == 1) {
                                                res_cancle(cn, id, pw);
                                                break;
                                            } else if (cnum == 2) {
                                                System.out.println("처음 화면으로 돌아갑니다.");
                                                break;
                                            } else {
                                                System.out.println("정확한 번호를 입력해주세요.");
                                            }
                                        }
                                    } else {
                                        System.out.println("\n!!! 확인 가능한 예약내역이 없습니다. !!!");
                                        System.out.println("!!! 정보를 다시 입력해주세요");
                                        continue test;
                                    }
                                } else {
                                    System.out.println("\n사용자 정보가 없습니다. 다시 입력해주세요");
                                    System.out.format(" !!! 입력하신 [예매자 성함] : %s | [ID]: %s  | [PW] : %s !!!\n", cn, id, pw);
                                    System.out.println();
                                    continue test;
                                }
                            }
                            System.out.println("프로그램 종료");
                            break;
                        } catch (NullPointerException ne) {
                            System.out.println("!!! 잘못 입력하셨습니다. 정확하게 입력해주세요 !!!\n");
                            System.out.println("성함과 ID는 영문으로, PW는 숫자만 입력해주세요");
                            continue;
                        }
                    }
            }
        } catch (Exception e) {
            System.out.println("올바른 번호를 입력해주세요 : " + e.getMessage());

        }
    }
    public void res_cancle(String customer_name, String cus_id, String cus_pw) {
        StringBuilder sb = new StringBuilder(RESERVATION_CANCEL);
        sb.append(" = \"" + "NO\"");
        sb.append(" WHERE " + COLUMN_PER_INFO_NAME + " = ? ");
        sb.append(" AND " + COLUMN_PER_INFO_ID + " = ? ");
        sb.append(" AND " + COLUMN_PER_INFO_PW + " = ? ");

        StringBuilder sb1 = new StringBuilder(RESERVATION_ID_CANCEL + "= NULL");
        sb1.append(" WHERE " + COLUMN_SEAT_TABLE_RESERVATION_ID + " = ? ");


        try {
            PreparedStatement ps = conn.prepareStatement(sb.toString());
            ps.setString(1, customer_name);
            ps.setString(2, cus_id);
            ps.setString(3, cus_pw);

            PreparedStatement ps1 = conn.prepareStatement(sb1.toString());
            ps1.setString(1, cus_id);

            int cancle_result = ps.executeUpdate();
            int cancle_result1 = ps1.executeUpdate();
            if (cancle_result == 1) {
                System.out.println("### 예매취소 중입니다. ###");
                if (cancle_result1 == 1) {
                    System.out.println("### 예매취소가 정상적으로 완료되었습니다. ###");
                } else {
                    System.out.println("!!! 예매자의 좌석 정보가 없습니다!!!");
                }
            } else {
                System.out.println("!!! 예매가 취소되지 않았습니다!!!");
            }

        } catch (Exception e) {
            System.out.println("에러 메세지 : " + e.getMessage());
        }
    }


    public ArrayList<Mem_info> input_cusInfo(String customer_name, String cus_id, String cus_pw) {



        StringBuilder str = new StringBuilder(RESERVATION_INQUIRY_QUERY);
        str.append(" WHERE " + COLUMN_PER_INFO_NAME + " = ").append("\"" + customer_name + "\"");
        str.append(" AND ").append(COLUMN_PER_INFO_ID + " = ").append("\"" + cus_id + "\"");
        str.append(" AND ").append(COLUMN_PER_INFO_PW + " = ").append(cus_pw);


        try (Statement statement = conn.createStatement()) {
            ResultSet results = statement.executeQuery(str.toString());

            ArrayList<Mem_info> cus_info = new ArrayList<>();
            while (results.next()) {
                Mem_info info = new Mem_info();
                info.setName(results.getString(COLUMN_PER_INFO_NAME));
                info.setID(results.getString(COLUMN_PER_INFO_ID));
                info.setPW(results.getString(COLUMN_PER_INFO_PW));
                info.setRes_check(results.getString(COLUMN_PER_INFO_RES_CHECK));
                cus_info.add(info);


            }
            return cus_info;
        } catch (Exception e) {
            return null;
        }
    }
//    =================================================================================================================
//    =============(항공권 예약 조회)=====================================================================================
    public void checkReservation (){
        String str ;
        System.out.printf("2.항공권 조회하기입니다.");

        try{
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_NAME_FROM_PER_INFO);
            ArrayList<String> name = new ArrayList<>();
            while(resultSet.next()){
                Mem_info info_name = new Mem_info();
                info_name.setName(resultSet.getString(COLUMN_PER_INFO_NAME));
                name.add(info_name.getName());
            }
            do{
                System.out.println("회원의 이름을 입력해주세요");
                Scanner scanner = new Scanner(System.in);
                str = scanner.nextLine();

                if(name.contains(str)){
                    System.out.println("확인 되었습니다");
                }else {
                    System.out.println("일치 하는 이름이 없습니다 . 다시 입력해주세요");
                }

            }while(!name.contains(str));
            String str1;
            String str2;

            Statement statement2 = conn.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(SELECT_ID_PW_FROM_PER_INFO + " WHERE name = " + "'" + str + "'");
            HashMap<String , String> name_ID = new HashMap<>();
            name_ID.put(resultSet2.getString(COLUMN_PER_INFO_ID), resultSet2.getString(COLUMN_PER_INFO_PW));
            Scanner scanner;
            do {
                System.out.println("회원의 아이디를 입력해주세요");
                scanner = new Scanner(System.in);
                str1 = scanner.nextLine();
                if (name_ID.containsKey(str1)) {

                } else {
                    System.out.println("등록되지 않은 아이디입니다");
                }
            } while (!name_ID.containsKey(str1));
            do{
                System.out.println("회원의 비밀번호를 입력해주세요");
                str2 = scanner.nextLine();

                if (name_ID.get(str1).equals(str2)) {
                    System.out.println("잠시만 기다려주세요");

                }else {
                    System.out.println("비밀번호가 틀렸습니다 다시 입력하여 주세요");
                }
            }while(!(name_ID.get(str1).equals(str2)));

            ResultSet resultSet1 = statement.executeQuery(SQL_RESERVATION_INFORMATION  + "'" + str1 + "'" );

            String plane_name = resultSet1.getString(COLUMN_PLANE_SCH_PLANE_NAME);
            String date = resultSet1.getString(COLUMN_PLANE_SCH_DATE);
            String dep_city = resultSet1.getString(COLUMN_PLANE_SCH_DEP_CITY);
            String dep_time = resultSet1.getString(COLUMN_PLANE_SCH_DEP_TIME);
            String arri_time = resultSet1.getString(COLUMN_PLANE_SCH_ARRI_TIME);
            String arri_city = resultSet1.getString(COLUMN_PLANE_SCH_ARRI_CITY);
            String flight_time = resultSet1.getString(COLUMN_PLANE_SCH_FLIGHT_TIME);
            String seat_class = resultSet1.getString(COLUMN_SEAT_TABLE_SEAT_CLASS);
            String seat_name = resultSet1.getString(COLUMN_SEAT_TABLE_SEAT_NAME);

            System.out.println("항공기명 = " + plane_name + "\n비행날짜 = " + date + "\n출발도시 = " + dep_city
                    +"\n출발시간 = "+ dep_time + "\n도착시간 = " + arri_time +"\n도착도시 = "+ arri_city
                    +"\n비행시간 = "+ flight_time + "\n좌석등급 = " + seat_class+ "\n좌석번호 = "+ seat_name);

            ResultSet resultSet3 = statement.executeQuery("SELECT IFNULL(MAX(reservation_ID),0)AS 'reservation_ID'" +
                    " from seat_table_return where reservation_ID =" +"'" + str1 + "'");
            if(resultSet3.getString("reservation_ID").equals("0")){
                System.out.println("편도만 예약하셨습니다");
            }else if (!resultSet3.getString("reservation_ID").equals("0")) {
                System.out.println("돌아오시는 항공편 스케줄입니다");


                ResultSet resultSet4 = statement.executeQuery(SQL_RESERVATION_INFORMATION_RETURN + "'" + str1 + "'");

                String plane_name_return = resultSet4.getString(COLUMN_PLANE_SCH_PLANE_NAME);
                String date_return = resultSet4.getString(COLUMN_PLANE_SCH_DATE);
                String dep_city_return = resultSet4.getString(COLUMN_PLANE_SCH_DEP_CITY);
                String dep_time_return = resultSet4.getString(COLUMN_PLANE_SCH_DEP_TIME);
                String arri_time_return = resultSet4.getString(COLUMN_PLANE_SCH_ARRI_TIME);
                String arri_city_return = resultSet4.getString(COLUMN_PLANE_SCH_ARRI_CITY);
                String flight_time_return = resultSet4.getString(COLUMN_PLANE_SCH_FLIGHT_TIME);
                String seat_class_return = resultSet4.getString(COLUMN_SEAT_TABLE_SEAT_CLASS);
                String seat_name_return = resultSet4.getString(COLUMN_SEAT_TABLE_SEAT_NAME);

                System.out.println("항공기명 = " + plane_name_return + "\n비행날짜 = " + date_return + "\n출발도시 = " + dep_city_return
                        + "\n출발시간 = " + dep_time_return + "\n도착시간 = " + arri_time_return + "\n도착도시 = " + arri_city_return
                        + "\n비행시간 = " + flight_time_return + "\n좌석등급 = " + seat_class_return + "\n좌석번호 = " + seat_name_return);

            }

        }catch (SQLException E1){
            System.out.println("예약된 정보가 없습니다");
            System.out.println(E1.getMessage());
            E1.printStackTrace();
        }

    }
//=====================================================================================================================
// ==========================(회원가입)=================================================================================

    public void member_joining() {
        int a ;
        do{
            System.out.println("회원가입을 진행하시려면 0번 돌아가시려면 1 번을 눌러주세요");
            Scanner sc = new Scanner(System.in);
            try{
            a= sc.nextInt();
            }catch(InputMismatchException ie){
                System.out.println("숫자만 입력해주세요");
                continue;
            }
            if(a==0){
                System.out.println("회원가입 창으로 넘어갑니다");
                joining_checkName();
                break;
            } else if (a==1) {
                frontWindow();
            } else {
                System.out.println("0 또는 1만 입력해주세요");
            }
        }while (true);
    }

    public void joining_checkName(){
        String name;
        String firstname;
        String lastname;
        String phone;
        String regEx ="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        String regEx1 = "^[a-zA-Z]*$";

        try {
            boolean s;
            do {
                Scanner sc1 = new Scanner(System.in);
                System.out.println("first name 을 입력해주세요");
                firstname = sc1.nextLine();
                s = Pattern.matches(regEx1, firstname);
                if (s == true) {

                } else {
                    System.out.println("영문자로만 입력해주세요");
                }
            } while (!s);
            boolean q;
            do{
                Scanner sc2 =new Scanner(System.in);
                System.out.println("last name 을 입력해주세요");
                lastname = sc2.nextLine();
                q = Pattern.matches(regEx1 , lastname);
                if(q ==true){

                }else {
                    System.out.println("영문자로만 입력해주세요");
                }
            }while(!q);

            boolean t;
            do {
                Scanner sc2 = new Scanner(System.in);
                System.out.println("핸드폰 번호를 입력해주세요 EX) 010-2121-4242");
                phone = sc2.nextLine();
                t = Pattern.matches(regEx, phone);
                if (t == true) {

                } else {
                    System.out.println("형식에 맞게 입력하세요 EX) 010-2121-4242");
                }
            } while (!t);
            int a;
            do {
                name = firstname+" "+lastname;
                System.out.println("입력하신 성함과 핸드폰 번호가" + name + " " + phone + "이 맞습니까?");
                System.out.println("맞으시면 1번 재입력을 원하시면 2번을 눌러주세요");
//                Scanner sc3 = new Scanner(System.in);
//                a = sc3.nextInt();
                try {
                    Scanner sc3 = new Scanner(System.in);
                    a = sc3.nextInt();
                    if (a == 1) {
                        System.out.println("1번 누르셨습니다");
                        break;
                    } else if (a == 2) {
                        joining_checkName();
                    } else {
                        System.out.println("1 또는 2로만 입력해주세요");
                    }
                } catch (InputMismatchException E) {
                    System.out.println("숫자만 입력 가능합니다 문자는 입력하시마세요");
                }
            } while (true);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IFNULL(MAX(name),'NO')AS 'name' ," +
                    "IFNULL(MAX(phone),'NO')AS 'phone' FROM " +
                    "per_info WHERE name = " + "'"+name+"' " + "AND " + "phone= " + "'"+phone+"'");
            HashMap<String, String> name_phone = new HashMap<>();
            name_phone.put(resultSet.getString("name"), resultSet.getString("phone"));
            if (name_phone.containsKey("NO")) {
                Insert_INFO(name, phone);
            } else if (name_phone.containsKey(name) && name_phone.containsValue(phone)) {
                System.out.println("이미 가입된 회원입니다");

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void Insert_INFO(String name , String phone){

        String regID = "^[a-zA-Z]*$";
        String regPW = "^[0-9]*$";
        String regBirthdate = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
        String regNationality = "^[a-zA-Z]*$";
        String regGender = "^[a-zA-Z]*$";
        String ID;
        String PW1;
        String PW2;
        String Birthdate;
        String Nationality;
        String Gender;
        try {

            boolean z;
            ArrayList<String> mem_infos;
            do {
                Statement statement1 = conn.createStatement();
                ResultSet resultSet = statement1.executeQuery("SELECT ID FROM per_info");
                mem_infos = new ArrayList<>();
                while (resultSet.next()) {
                    Mem_info mem_info = new Mem_info();
                    mem_info.setID(resultSet.getString("ID"));
                    mem_infos.add(mem_info.getID());
                }
//                System.out.println(mem_infos);
                System.out.println("원하시는 아이디를 영문자로만 입력해주세요");
                Scanner scanner1 = new Scanner(System.in);
                ID = scanner1.next();
                z = Pattern.matches(regID, ID);
                if (z == true && !mem_infos.contains(ID)) {
                    System.out.println("사용 가능한 아이디 입니다");
                } else if (z == true && mem_infos.contains(ID)) {
                    System.out.println("이미 등록된 아이디입니다 다른 아이디를 사용하세요");
                } else {
                    System.out.println("영문자로만 입력해주세요");
                }
            } while (!(z == true && !mem_infos.contains(ID)));
            boolean a;
            boolean b;
            do {
                System.out.println("원하시는 비밀번호를 숫자로만 입력해주세요");
                Scanner scanner2 = new Scanner(System.in);
                PW1 = scanner2.next();
                System.out.println("입력하신 비밀번호를 한번 더 입력해주세요");
                PW2 = scanner2.next();
                a = Pattern.matches(regPW,PW1);
                b = Pattern.matches(regPW,PW2);
                if (b==true && a == true && PW1.equals(PW2)) {
                } else if (b==true && a==true && PW1 !=PW2) {
                    System.out.println("입력하신 비밀번호 두개가 다릅니다");
                } else {
                    System.out.println("숫자로만 입력해주세요");
                }
            } while (!(b==true && a==true && PW1.equals(PW2)));
            boolean c;
            do {
                System.out.println("생년월일을 형식에 맞게 작성해주세요 ex) 1994-04-04 ");
                Scanner scanner3 = new Scanner(System.in);
                Birthdate = scanner3.next();
                c = Pattern.matches(regBirthdate, Birthdate);
                if (c == true) {
                } else {
                    System.out.println("형식에 맞게 작성해주세요");
                }
            } while (!c);
            boolean d;
            do {
                System.out.println("성별을 적어주세요 ex) male / female");
                Scanner scanner4 = new Scanner(System.in);
                Gender = scanner4.next();
                d = Pattern.matches(regGender, Gender);
                if (d == true && Gender.equals("male") || Gender.equals("female")) {
                } else {
                    System.out.println("male 또는 female만 입력 가능합니다");
                }
            } while (!(d == true && Gender.equals("male") || Gender.equals("female")));
            boolean w;
            do {
                System.out.println("회원님의 국적을 적어주세요 ");
                Scanner scanner5 = new Scanner(System.in);
                Nationality = scanner5.next();
                w = Pattern.matches(regNationality, Nationality);
                if (w == true) {
                } else {
                    System.out.println("영문자로 입력해주세요");
                }
            } while (!w);
            do {
                try {//int[] a = new int[5];
                    System.out.println("성함 : " + name + " 아이디 : " + ID + " 비밀번호 : " + PW1 + " 생년월일 : " + Birthdate + " 성별 : " + Gender +
                            " 국적 : " + Nationality + " 핸드폰번호 : " + phone + " 가 맞으시면 0번을 다시 입력을 원하시면 1번을 눌러주세요");
                    Scanner sc = new Scanner(System.in);
                    int a1 = sc.nextInt();
                    if (a1 == 0) {
                        break;
                    } else if (a1 == 1) {
                        Insert_INFO(name, phone);
                    } else {
                        System.out.println("0 또는 1만 눌러주세요");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("숫자로만 입력해주세요");
                }
            } while (true);

            StringBuilder stringBuilder = new StringBuilder("INSERT INTO per_info (name,ID,PW,birthdate,gender,nationality,phone,res_check) VALUES");
            stringBuilder.append("(");
            stringBuilder.append("'" + name + "'" + ",");
            stringBuilder.append("'" + ID + "',");
            stringBuilder.append("'" + PW1 + "',");
            stringBuilder.append("'" + Birthdate + "',");
            stringBuilder.append("'" + Gender + "',");
            stringBuilder.append("'" + Nationality + "',");
            stringBuilder.append("'" + phone + "',");
            stringBuilder.append("'NO'");
            stringBuilder.append(")");
            Statement statement = conn.createStatement();
            statement.execute(stringBuilder.toString());

            System.out.println("회원가입이 완료되었습니다. 축하드립니다");


        }catch (SQLException e){

        }


    }
//=====================================================================================================================
//    =================(아이디 비밀번호 찾기)=============================================================================
    public void findPW (){
        String regID = "^[a-zA-Z]*$";
        String regphone ="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        try {
            boolean a;
            String firstname;
            do {
                System.out.println("first name을 입력해주세요");
                Scanner scanner1 = new Scanner(System.in);
                firstname = scanner1.next();
                a = Pattern.matches(regID, firstname);
                if (a == true) {

                } else {
                    System.out.println("영문자만 입력해주세요");
                }
            } while (!a);
            boolean z;
            String lastname;
            do {
                System.out.println("last name을 입력해주세요");
                Scanner scanner2 = new Scanner(System.in);
                lastname = scanner2.next();
                z = Pattern.matches(regID, lastname);
                if (z == true) {

                } else {
                    System.out.println("영문자만 입력해주세요");
                }
            } while (!z);
            String name = firstname+" "+lastname;
            boolean q;
            String phone;
            do {
                System.out.println("핸드폰 번호를 입력해주세요");
                Scanner scanner3 = new Scanner(System.in);
                phone = scanner3.next();
                q = Pattern.matches(regphone, phone);
                if (q == true) {

                } else {
                    System.out.println("형식에 맞게 작성해주세요");
                }
            } while (!q);

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT IFNULL(MAX(ID),0)AS 'ID'" +
                    " ,IFNULL(MAX(PW),0)AS 'PW' FROM per_info WHERE name" +
                    " = " + "'"+ name +"'" +" and "+"phone = "+ "'"+phone+"'");
            HashMap<String, String> ID_PW = new HashMap<>();
            String ID = resultSet.getString("ID");
            String PW = resultSet.getString("PW");
            ID_PW.put(ID, PW);

            if (ID.equals("0")) {
                System.out.println("입력하신 정보가 일치하지 않습니다 다시 입려해주세요");
                System.out.println("입력하신 이름 : " + name + ", 입력하신 핸드폰 번호  : " + phone );
                findPW();
            } else if (PW.equals(ID_PW.get(ID))) {
                System.out.println("회원님의 아이디는 : " + ID + "  비밀번호는 : " + PW + " 입니다");
            }
        }catch (SQLException e){
            System.out.println();
        }
    }



}
