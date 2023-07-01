# minecraft-hjun-event-canceller-plugin
hjun's minecraft event canceller plugin

//계획
    azure 서버의 paperconfig에 player 충돌 허용하기
    /hec tp sp <- 정상 작동하는지 체크
    /hec placeblock,breakblock,teleport splist 고치기 - 작동 안함
    config.yml에 있는 셋팅 가져오는 명령어 만들기 - console에서만 사용 가능하게 하기
    특정 변수만 저장하게 하는 셋팅 명령어 만들기
    이미 해당 플레이어 설정이 되어있는 경우에 다른 메시지 주기 EX) 현재 이미 (sp)에게 블럭 설치가 허용되어 있습니다.
    
/hec placeblock [true, false] - 블럭 설치 [true, false]  0
                sp (specific player) [true, false] - 특정 플레이어가 블럭 설치 [true, false]   0
                splist   0
/hec breakblock [true, false] - 블럭 파괴 [true, false]  0
                sp (specific player) [true, false] - 특정 플레이어가 블럭 파괴 [true, false]   0
                splist   0
/hec tp[(teleport] allplayer [true, false] - 모든 플레이어가 tp 하는 것을 [true, false]   0
                   tome [true, false] - 모든 플레이어가 나에게 tp 하는 것을 [true, false]   0
                   tome sp (specific player) [true, false] - 나에게 특정 플레이어가 tp 하는 것을 [true, false] X
                   tosp  [true, false] - 특정 플레이어에게 tp 하는 것을 [true, false]    X
                   sp (specific player) [true, false] - 특정 플레이어가 tp 하는 것을 [true, false] 0
                   splist - /hec tp sp 명령어의 플레이어 리스트
/hec tntexplode [true, false] - tnt 폭발 [true, false] 0
/hec creeperexplode [true, false] - creeper 폭발 [true, false] 0
/hec explode [true, false] - 모든 폭발 [true, false] 0
/hec spreadevent [true, false] - 불+머쉬룸이 번지는 것(불을 일으키는 것은 허용) [true, false]   0
/hec blockignite [true, false] - 블럭 연소 [true, false] 0
/hecc lock [true, false] - 플레이어들이 명령어로 해당 플러그인의 설정을 변경하는 것을 [true, false] - console에서만 사용 가능 0
/hecc reload - config.yml 설정 불러오기 <- 설정 기능을 넣어 더 많은 변수를 선택적으로 불러올 수 있게 하기
// sp = specific player
