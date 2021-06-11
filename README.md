# LookUP

 ### 딥러닝을 활용한 스마트 옷장 관리 어플리케이션   
 
 <img width="700" alt="2" src="https://user-images.githubusercontent.com/55148494/121532534-636e0280-ca3a-11eb-9326-a96613e89fe5.JPG">      


## CTRL_S 유튜브 채널
https://www.youtube.com/channel/UCD8cOZayQ3FosrTvUVdCQqg      



## 시연영상 링크
https://www.youtube.com/watch?v=xOWpKh46e6E



## 프로젝트 소개
최근 패션은 트렌드가 빠르게 변화하여,  일명 패스트 패션으로 불린다.    
때문에 방치되는 옷이 증가하고, 
무슨 옷이 있는지 기억도 못한채          
다시 옷을 구입하게되어 불필요한 소비를 하게 되는 악순환이 반복되고 있다.   
환경부의 통계에 따르면, 2014년 하루 약 214톤 이상의 의류 폐기물이 발생하였고,    
현시점에는 더욱 증가하였을 것이라 예상된다.    

이러한 문제점을 해결할 필요성을 느껴 LookUP 을 고안하게 되었으며    
프로젝트는 사용자의 선호도, 외출목적, 날씨를 고려한     
맞춤형 코디 추천과 스마트 옷장 관리를 위한 어플리케이션 제작을 목표로 하였다.       



## 시스템 흐름도 및 구조도
 
<img width="700" alt="1" src="https://user-images.githubusercontent.com/55148494/121527289-34a15d80-ca35-11eb-9e24-cedcef0b54ab.JPG">    



## 사용 기술 소개   
1. 이미지 윤곽선 추출

   <img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121529296-3b30d480-ca37-11eb-8bea-36b388d3aa30.png">   
   
2. 딥러닝 모델

   <img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121529279-38ce7a80-ca37-11eb-967f-a49f32be1f53.JPG">   
   
   <img width="600" alt="2" src="https://user-images.githubusercontent.com/55148494/121529283-39671100-ca37-11eb-98cf-2fb6e49947d9.png">    
   
   <img width="600" alt="3" src="https://user-images.githubusercontent.com/55148494/121529288-39ffa780-ca37-11eb-8982-a1eeb5edec3c.JPG">    
   
3. 선호도 기반 룩북 제작

   <img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121529292-3a983e00-ca37-11eb-8b50-8e7eaaeab57d.png">    
   
   <img width="600" alt="2" src="https://user-images.githubusercontent.com/55148494/121529295-3a983e00-ca37-11eb-8b4a-e676a7314d89.png">         




## Look UP 대표 기능 3가지      

<img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121531399-5270c180-ca39-11eb-9f5a-6a180ce5a759.JPG">   

1. 스타일 분석

    1-1. 스타일별 선호도 조사
  	 
     <img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121662974-e5b10200-cae0-11eb-8079-c4fabe64bf56.png">
  
    1-2. 외출목적 별 선호도 조사
    
     <img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121546467-463f3100-ca46-11eb-9145-186a4cdff885.JPG">
  

2. 옷장 관리

    2-1. 옷 추가 
    
     <img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121662361-36742b00-cae0-11eb-950a-989b5d296692.png">
     
     <img width="600" alt="2" src="https://user-images.githubusercontent.com/55148494/121662367-37a55800-cae0-11eb-93ef-a6f514226904.png">
  
    2-2. 옷장 관리
	
     <img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121532179-138f3b80-ca3a-11eb-90d4-3648965564a1.png">
	 

3. 룩북
   
   <img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121528654-a62ddb80-ca36-11eb-8249-523ef8bef437.png">




## 기대효과 및 향후 개발 계획   

<img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121538383-b4342a00-ca3f-11eb-96fd-5419118fd53e.JPG">




## 개발 환경   

<img width="600" alt="1" src="https://user-images.githubusercontent.com/55148494/121531816-babfa300-ca39-11eb-9361-5862fa1dd67d.png">




## 기술 블로그
- 김연재 https://yj-tech.tistory.com/

   => CNN모델을 위한 데이터 전처리, 옷 분류 CNN모델, 유사 RGB값을 활용한 배경 제거

- 신민정 https://mj-lab.tistory.com/

   => Keras를 활용한 옷 분류 CNN 모델, 데이터셋 수집, 서버에서 모델 예측 값 받아 오기

- 이소림 https://sr-algo.tistory.com/

   => train&validation set 수집, CNN 모델 최적화, 텍스트 유사도를 활용한 옷 조합



## Reference

- OpenCV grabcut을 이용한 이미지 윤곽선 추출 

  https://deep-learning-study.tistory.com/240

- 유사 RGB 값을 선택하여 자동 / 수동 배경 색상 제거

  https://github.com/GabrielBB/Android-CutOut
  
- CNN model

  https://buomsoo-kim.github.io/keras/2018/05/05/Easy-deep-learning-with-Keras-11.md/
  
- 컨텐츠 기반 추천: 텍스트 유사도

  https://techblog-history-younghunjo1.tistory.com/115
