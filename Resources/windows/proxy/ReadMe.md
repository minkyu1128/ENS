### ens.zip 파일은..
* servlet 매핑을 통한 proxy 서비스를 제공 한다.
* 필수 적용사항은 아니므로 서비스 환경에 따라 적용하도록 한다.

### 파일 설명
|파일명|서비스 URL|파라미터|호출자|설명|
|---|---|---|---|---|
|notiPrnt.jsp|/kko/mydoc/noti/prnt|``document_binder_uuid:`` 카카오페이의 문서식별키<p/>``external_document_uuid:`` 이용시스템의 문서식별키(bulk 전송 시에만 해당)</p>``token:`` 원타임토큰(OTT)|카카오페이|전자고지시스템(ens)이 제공하는 ``인증톡에 대한 고지출력의 기능을 사용하는 경우`` Host 설정을 위한 용도|
|urlImgPrnt.jsp|/img/prnt|``link:`` 이미지 URL<p/>``type:`` 이미지타입(jpg/png/gif)|사용자(=인증톡 수신자)|이미지에 대한 링크가 서버(내부)에서만 접근이 가능한 경우에 사용하기 위한 용도|

### 배포방법
* ``~/webapps 경로에 zip 파일을 unzip`` 한다.
* ``~/conf/server.xml`` 파일에 <Context> 정보를 추가 한다.
```
	<Context path="/ens" docBase="{톰캣설치 경로}/webapps/ens" debug="0" reloadable="true"/>
```