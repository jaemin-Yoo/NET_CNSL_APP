# Project: NET_CNSL_APP
* 스쿨존 CCTV를 활용한 스마트 내비게이션
* NET 챌린지 캠프 시즌7 챌린지리그
* 주최 :
* 주관 :
* 2020.05 ~ 2020.12

<img src="/doc/imgs/ppt1.PNG" width="100%">

## 0. Overview
스쿨존에서의 어린이 사고는 매해 끊임없이 발생하고 있다.
차안에서 운전자가 어른들에 비해 상대적으로 작은 어린이들을 식별하는 것은 쉽지않고, 사각지대에 있는 보행자가 갑자기 튀어나온다면 위험한 상황이 발생 할 것이다.
이러한 문제를 해결하기 위해, 스쿨존에서 실시간으로 cctv를 통해 어린이 보행자 위치를 파악함으로써 사고를 방지 및 인명피해를 줄이는 것이다.

## 1. Goals
- 운전자가 스쿨존에서 보행자 및 어린이의 위치를 보기 쉽게 내비게이션 지도상에 나타냄으로써 보다 신속하고 정확하게 상황을 판단할 수 있다.
- 운전자가 볼 수 없는 사각지대에서 어린이가 갑자기 튀어나오는 갑작스러운 상황에 사고를 예방할 수 있다.

## 2. Function
- hog 알고리즘을 사용한 보행자 검출
<img src="/doc/imgs/ppt3.PNG" width="70%">  
CCTV에서 사람을 인식하기 위해서 픽셀의 그래디언트 방향 성분을 히스토그램으로 객체의 상태를 추출 분류하고,  특징 벡터를 추출하여 보행자를 분류하는 방식인 hog알고리즘을 이용해서 보행자를 검출 하였다.

- perspective 함수를 사용하여 3D 좌표를 2D좌표로 매핑
<img src="/doc/imgs/ppt4.PNG" width="50%">
- 인식된 사람의 좌표를 GPS좌표로 변환하기 위한 구역을 설정하여 2D좌표로 매핑합니다.
매핑된 2D좌표를 이용하여 GPS변환을 하게 되는 것입니다.

- 픽셀 좌표를 GPS 좌표로 변환
<img src="/doc/imgs/ppt5.PNG" width="50%">
<img src="/doc/imgs/ppt6.PNG" width="50%">
<img src="/doc/imgs/ppt7.PNG" width="50%">

- 다중 CCTV를 활용한 사각지대 문제 해결 및 정확도 개선
<img src="/doc/imgs/ppt8.PNG" width="50%">
<img src="/doc/imgs/ppt9.PNG" width="50%">
<img src="/doc/imgs/ppt10.PNG" width="50%">
### SDI Server
- HPC서버에서 전송한 GPS좌표 데이터를 저장하고 관리한다.
- CCTV와 보행자 정보 (person) 를 관리하는 Data Table이 존재한다.
- CCTV Table에서는 각 CCTV의 위치정보(위도, 경도)와 IP 정보가 저장되어 관리된다. 해당 정보는 지속적으로 SDI 서버에 자신의 GPS 정보를 전송하는 운전자 APP의 위치가 스쿨존 반경 내에 위치하는지 비교 및 확인하는 과정에서 사용된다.
- person Table에서는 각 스쿨존의 지정 반경 내에 존재하는 보행   자의 위치정보(위도, 경도), 보행자를 인식한 cctv의 ip주소와 시각 정보가 저장되어 관리된다.

### HPC Server
- 각 스쿨존에 설치된 다수의 CCTV에서 수신된 GPS 데이터를 바탕으로 스쿨존의 다중 CCTV에서 추출한 위치정보들을 결합하여 보행자의 위치를 보다 정확하게 파악하고 사각지대 문제를 해결한다.
- CCTV에서 수신한 보행자 GPS데이터를 KOREN망을 통해 실시간으로 처리하므로 스쿨존 내 교통 상황을 신속하게 확인할 수 있다.

## 3. Flow
<img src="/doc/imgs/img1.png" width="50%"> <img src="/doc/imgs/img2.png" width="50%">  
<img src="/doc/imgs/ppt2.PNG" width="70%">


## 4. UI
|앱|서버|CCTV|
|---|---|---|
|- 실시간으로 서버에 단말기의 위치정보를 전송하여 어린이 보호구역에 위치하는지 파악한다.</br></br>- SDI서버에서 수신받은 보행자 위치정보를 빨간 마커의 형태로 표시한다.</br></br>- 차량에 인접한 거리에 있는 보행자는 마커의 색깔이 진하게 표시되거나 경고알림 등으로 위험정도를 알린다.|**HPC**</br>- SDI, CCTV와 소켓 통신이 가능하다.</br>- 다중 CCTV에서 수신 받은 위치 정보를 이용하여 사람 위치 오차를 최소화하고 사각지대 문제를 해결한다.</br></br>**SDI**</br>- HPC와 소켓 통신 및 앱과 Http통신이 가능하다.</br>- 보행자의 위치정보를 데이터베이스에 저장한다.</br>- 저장된 정보를 앱에 전송하여 보행자의 위치를 표시한다.|- 사람을 인식하고 2D 좌표를 이용하여 지도상의 보행자 위치 좌표를 추출한다.</br></br>- 추출한 좌표를 HPC에 전달한다.|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/doc/imgs/result01.png" width="50%">|<img src="/doc/imgs/result02.png" width="100%">|<img src="/doc/imgs/result03.png" width="100%">|

<img src="/doc/imgs/ppt11.PNG" width="100%">
