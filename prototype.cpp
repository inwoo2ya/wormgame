# include <cstdio>
# include <cstring>
# include <list>
# include <queue>
# include <vector>
# include <windows.h>
# define	MAP_SIZE	10

using namespace std;

typedef struct { int x, y; }pos;
typedef struct {
	pos head, body[2];
	int health;
}worm;
typedef struct {
	int id;
	list<worm> worm_list;
	pos bomb;
	char map[MAP_SIZE][MAP_SIZE];
	bool gameover;
}player;

list<player> player_list;
int dx[]={-1, -1, 0, 1, 1, 1, 0, -1}, dy[]={0, 1, 1, 1, 0, -1, -1, -1};

bool poscmp(const pos &a, const pos &b) { return a.x!=b.x||a.y!=b.y; }
worm init_worm(void) {
	worm w;
	char str[101];
	w.health=2;
	printf("지렁이의 머리(A0~J9) : ");
	scanf("%s", str);
	while(strlen(str)!=2||str[0]<'A'||str[0]>'J'||str[1]<'0'||str[1]>'9') {
		puts("");
		puts("잘못된 입력입니다. 다시 입력해주세요.");
		printf("지렁이의 머리(A0~J9) : ");
		scanf("%s", str);
	}
	w.head.x=str[0]-'A';
	w.head.y=str[1]-'0';
	printf("\n지렁의 머리 - %s\n", str);
	printf("지렁이의 방향(0 - 북쪽, 0~7 - 시계방향) : ");
	int vec;
	scanf("%d", &vec);
	while(vec<0||vec>7||w.head.x+2*dx[vec]>=MAP_SIZE||w.head.x+2*dx[vec]<0||w.head.y+2*dy[vec]>=MAP_SIZE||w.head.y+2*dy[vec]<0) {
		puts("");
		puts("잘못된 입력입니다. 혹은 지렁이의 몸이 맵을 벗어납니다. 다시 입력해주세요.");
		printf("지렁이의 머리 - %s\n", str);
		printf("지렁이의 방향(0 : 북쪽, 0~7 - 시계방향) : ");
		scanf("%d", &vec);
	}
	for(int i=0 ; i<2 ; i++) {
		w.body[i].x=w.head.x+(i+1)*dx[vec];
		w.body[i].y=w.head.y+(i+1)*dy[vec];
	}
	return w;
}
void print_player_map(char map[MAP_SIZE][MAP_SIZE]) {
	printf("\n  ");
	for(int i=0 ; i<MAP_SIZE ; i++)
		printf("%d ", i);
	puts("");
	for(int i=0 ; i<MAP_SIZE ; i++) {
		printf(" %c", 'A'+i);
		for(int j=0 ; j<MAP_SIZE ; j++)
			printf("%c ", map[i][j]);
		puts("");
	}
	puts("");
}
player init_player(int id) {
	player p;
	p.gameover=false;
	p.id=id;
	for(int i=0 ; i<MAP_SIZE ; i++)
		for(int j=0 ; j<MAP_SIZE ; j++)
			p.map[i][j]=' ';
	for(int i=1 ; i<=3 ; i++) {
		system("cls");
		printf("Player %d의 %d번째 지렁이를 등록합니다.\n", p.id, i);
		print_player_map(p.map);
		worm w=init_worm();
		p.map[w.head.x][w.head.y]='h';
		for(int j=0 ; j<2 ; j++)
			if(p.map[w.body[j].x][w.body[j].y]!='h')
				p.map[w.body[j].x][w.body[j].y]='o';
		p.worm_list.push_back(w);
	}
	//폭탄 등록 코드
	system("cls");
	printf("Player %d의 폭탄을 등록합니다.\n", p.id);
	print_player_map(p.map);
	char str[101];
	printf("폭탄 위치(A0~J9) : ");
	scanf("%s", str);
	while(strlen(str)!=2||str[0]<'A'||str[0]>'J'||str[1]<'0'||str[1]>'9'||p.map[str[0]-'A'][str[1]-'0']!=' ') {
		puts("");
		puts("잘못된 입력입니다. 혹은 폭탄이 지렁이의 자리에 있습니다. 다시 입력해주세요.");
		printf("폭탄 위치(A0~J9) : ");
		scanf("%s", str);
	}
	p.bomb.x=str[0]-'A';
	p.bomb.y=str[1]-'0';
	p.map[p.bomb.x][p.bomb.y]='B';
	return p;
}
bool isgameover(void) {
	int liveduser=0;
	for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++)
		if(!p->gameover)
			liveduser++;
	if(liveduser<=1)
		return true;
	return false;
}
void game(void) {
	char str[101];
	queue<pair<int, pos>> q, bomb;	//pair<p.id, <x, y>>
	for(int turn=1 ; !isgameover() ; turn++) {
		//플레이어별 좌표 입력 
		for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++) {
			system("cls");
			for(int i=0 ; i<50 ; i++)
				printf("=");
			printf("\nTURN : %d\n\n", turn);
			
			
			if(p->gameover) {
				printf("Player %d는 게임오버입니다.\n\n", p->id);
				system("pause");
			} else {
				//공격코드
				printf("Player %d의 공격차례입니다.\n\n", p->id);
				print_player_map(p->map);
				printf("공격 좌표(A0~J9) : ");
				scanf("%s", str);
				while(strlen(str)!=2||str[0]<'A'||str[0]>'J'||str[1]<'0'||str[1]>'9'||p->map[str[0]-'A'][str[1]-'0']=='X') {
					puts("");
					puts("잘못된 입력입니다. 혹은 누군가 이미 공격했던 좌표입니다. 다시 입력해주세요.");
					printf("공격 좌표(A0~J9) : ");
					scanf("%s", str);
				}
				q.push({p->id, {str[0]-'A', str[1]-'0'}});
			}
		}
		
		system("cls");
		//공격 commit
		while(!q.empty()) {
			
			//누구의 공격인가?
			pos atk=q.front().second;
			printf("Player %d의 공격 : %c%d\n", q.front().first, atk.x+'A', atk.y);
			q.pop();
			
			for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++) {
				if(p->map[atk.x][atk.y]=='X')
					continue;
						
				//데미지 
				for(list<worm>::iterator w=p->worm_list.begin() ; w!=p->worm_list.end() ; w++) {
					if(!poscmp(atk, w->head)) {
						printf("Player %d의 지렁이는 헤드샷 당했습니다.\n", p->id);
						if(p->map[w->body[0].x][w->body[0].y]!='X')
							p->map[w->body[0].x][w->body[0].y]=' ';
						if(p->map[w->body[1].x][w->body[1].y]!='X')
							p->map[w->body[1].x][w->body[1].y]=' ';
						printf("Player %d의 지렁이가 죽었습니다.\n", p->id);
						w=p->worm_list.erase(w);
						w--;
					} else if(!poscmp(atk, w->body[0])||!poscmp(atk, w->body[1])) {
						printf("Player %d의 지렁이는 데미지를 입었습니다.\n", p->id);
						w->health--;
						if(!w->health) {
							printf("Player %d의 지렁이가 죽었습니다.\n", p->id);
							p->map[w->head.x][w->head.y]=' ';
							w=p->worm_list.erase(w);
							w--;
						}
					}
					
				}
				p->map[atk.x][atk.y]='X';
				
				
				//게임오버 판별
				if(!p->worm_list.size()&&!p->gameover) {
					printf("Player %d는 게임오버입니다.\n\n", p->id);
					p->gameover=true;
				}
					
				//폭탄
				if(!poscmp(atk, p->bomb)) {
					printf("%c%d에는 Player %d의 폭탄이 있었습니다.\n\n", atk.x+'A', atk.y, p->id);
					bomb.push({p->id, {atk.x, atk.y}});
					p->bomb.x=-1;
					p->bomb.y=-1;
				}
				
				puts("");
			}
			
			 
		}
		
		//폭탄 commit
		while(!bomb.empty()) {
			//누구의 폭탄인가?
			pos atk=bomb.front().second;
			printf("Player %d의 폭탄 : %c%d\n\n", bomb.front().first, atk.x+'A', atk.y);
			bomb.pop();
			
			//폭탄 영역
			vector<pos> v;
			v.push_back(atk);
			for(int i=0 ; i<8 ; i++) {
				int nx=atk.x+dx[i];
				int ny=atk.y+dy[i];
				if(nx<0||nx>=MAP_SIZE||ny<0||ny>=MAP_SIZE)
					continue;
				v.push_back({nx, ny});
			}
			
			//데미지 및 맵 갱신
			for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++) {
				for(list<worm>::iterator w=p->worm_list.begin() ; w!=p->worm_list.end() ; w++)
					for(int i=0 ; i<v.size() ; i++) {
						if(p->map[v[i].x][v[i].y]=='X')
							continue;
						if(!poscmp(v[i], w->head)) {
							printf("Player %d의 지렁이는 헤드샷 당했습니다.\n", p->id);
							if(p->map[w->body[0].x][w->body[0].y]!='X')
								p->map[w->body[0].x][w->body[0].y]=' ';
							if(p->map[w->body[1].x][w->body[1].y]!='X')
								p->map[w->body[1].x][w->body[1].y]=' ';
							printf("Player %d의 지렁이가 죽었습니다.\n", p->id);
							w=p->worm_list.erase(w);
							w--;
						} else if(!poscmp(v[i], w->body[0])||!poscmp(v[i], w->body[1])) {
							printf("Player %d의 지렁이는 데미지를 입었습니다.\n", p->id);
							if(!--w->health) {
								printf("Player %d의 지렁이가 죽었습니다.\n", p->id);
								p->map[w->head.x][w->head.y]=' ';
								w=p->worm_list.erase(w);
								w--;
							}
						}
						
					}
				for(int i=0 ; i<v.size() ; i++)
					p->map[v[i].x][v[i].y]='X';
					
				if(!p->worm_list.size()&&!p->gameover) {
					printf("Player %d는 게임오버입니다.\n\n", p->id);
					p->gameover=true;
				}
			}
		
			//연쇄폭탄
			for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++)
				for(int i=0 ; i<v.size() ; i++)
					if(!poscmp(v[i], p->bomb)) {
						printf("%c%d에는 Player %d의 폭탄이 있었습니다.\n", v[i].x+'A', v[i].y, p->id);
						bomb.push({p->id, v[i]});
						p->bomb.x=-1;
						p->bomb.y=-1;
					}
		}
		system("pause");
	}
}
void result(void) {
	int winner;
	bool flag=false;
	for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++)
		if(!p->gameover) {
			flag=true;
			winner=p->id;
			break;
		}
	if(flag)
		printf("최후의 승자 : Player %d\n", winner);
	else
		puts("생존자가 아무도 없습니다.");
}

int main(void) {
	int N;
	
	puts("지렁이 게임 prototype");
	printf("플레이어 수 입력(2~4) : ");
	scanf("%d", &N);
	
	for(int i=1 ; i<=N ; i++) {
		player p=init_player(i);
		player_list.push_back(p);
	}
	
	game();
	result();
	return 0;
}
