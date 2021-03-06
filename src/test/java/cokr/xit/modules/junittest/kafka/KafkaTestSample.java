package cokr.xit.modules.junittest.kafka;


/* ====================================
 * 카프카(kafka) 참고 사이트
 *  -. 카프카란? 
 *    : https://freedeveloper.tistory.com/350
 *  -. 카프카 윈도우(windows)에 설치하기
 *    : https://cookiethecat2020.blogspot.com/2020/03/blog-post.html
 *    : https://jjjwodls.github.io/etc/2020/01/07/01-Kafka-Setup.html
 *  -. 카프카 기본 명령어 모음
 *    : https://velog.io/@zkdlu/kafka-1.kafka
 *  -. STS 카프카 연동하여 pub/sub 구현 (pub: Producer가 보낸 메시지, sub: Consumer가 가져간 메시지)
 *    : https://oingdaddy.tistory.com/308
 *  -. 프로듀서 모범 사례
 *    : https://team-platform.tistory.com/32
 *  -. 카프카 cluster 구성하고 Spring boot에서 Kafka 사용하기
 *    : https://sup2is.github.io/2020/06/03/spring-boot-with-kafka-cluster.html
 *  -. 카프카 properties 설정 정보 및 @KafkaListener를 등록하지 않고 직접 구현하는 방법
 *    : https://sunghs.tistory.com/80
 *  -. 카프카 DB 연동하기(Kafka Connect로 데이터 허브 구축하기)
 *    : 카프카 커넥트에서는 프로듀서/컨슈머 용어가 조금 바뀌는데 Source/Sink 라고 부른다.
 *    : https://sup2is.github.io/2020/06/08/kafka-connect-example.html
 *  
 *  
 *  도커(docker) 참고 사이트
 *   -.doc에 kafka설치
 *     : https://devfunny.tistory.com/429
  
 *   -.자주쓰는 명령어 모음
 *     1)stars 확인: docker search [image명]  //ex) docker search kafka
 *     2)이미지 설치: docker pull [이미지명]      //ex) docker pull wurstmeister/kafka, docker pull wurstmeister/zookeeper 
 *     3)컨테이너 접속: docker container exec -it [이미지명] [사용할 쉘]   //ex) docker container exec -it kafka bash
 *     4)도커 실행중인 프로세스 확인: docker ps
 *     5)여러개의 컨테이너로 구성된 어플리케이션 관리: docker-compose -f [파일명(yam 또는 yaml 파일) ] up(up는 한번에 생성하고 실행하기) -d(d는 백그라운드 실행옵션)
 *        ex) docker-compose -f c:\docker\docker-compose.yml up -d
 *        사이트참조: https://www.daleseo.com/docker-compose/ 
 *     6)Container 루트로 접속하기
 *       : sudo docker exec --user root  -it [컨테이너명] bash
 *        
 *        
 *  젠킨스(Jenkins) 참고 사이트
 *   -.시놀로지 docker에 젠킨스 설치&초기설정
 *     : https://www.bearpooh.com/49
 *     : https://www.bearpooh.com/50        
 *   -.svn(subversion) 형상관리 배포 빌드
 *     : https://myjamong.tistory.com/121  
 *   -.job별 JDK 버전 설정하기
 *     : https://goddaehee.tistory.com/257
 *   -.JDK 버전별 심볼릭링크 생성(update-alternatives) - 심볼릭링크의 장점은 긴경로의 fullpath를 alias로 사용할 수 있다.
 *     : https://skyoo2003.github.io/post/2017/03/17/what-is-alternatives-command
 *     심볼릭링크 실제 물리경로 찾기 => $readlink -f [심볼릭링크 path]
 *   -.파이프라인(pipline) SSH 배포 자동화
 *     : https://velog.io/@sihyung92/%EC%9A%B0%EC%A0%A0%EA%B5%AC2%ED%8E%B8-%EC%A0%A0%ED%82%A8%EC%8A%A4-%ED%8C%8C%EC%9D%B4%ED%94%84%EB%9D%BC%EC%9D%B8%EC%9D%84-%ED%99%9C%EC%9A%A9%ED%95%9C-%EB%B0%B0%ED%8F%AC-%EC%9E%90%EB%8F%99%ED%99%94
 *   -.Job 빌드 원격 유발
 *     : https://velog.io/@king/Jenkins-Job-%EC%8B%A4%ED%96%89%EC%9D%84-%EC%9B%90%EA%B2%A9%EC%9C%BC%EB%A1%9C-%EC%9C%A0%EB%B0%9C%ED%95%98%EA%B8%B0-nuk5jjenyk
 *     
 *     
 *  마리아(MariaDb) 참고 사이트
 *   -.MariaDb docker에 설치 하기
 *     : https://hospital82.tistory.com/180   
 ==================================== */
public class KafkaTestSample {

}
