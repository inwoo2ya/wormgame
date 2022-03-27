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
	printf("�������� �Ӹ�(A0~J9) : ");
	scanf("%s", str);
	while(strlen(str)!=2||str[0]<'A'||str[0]>'J'||str[1]<'0'||str[1]>'9') {
		puts("");
		puts("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
		printf("�������� �Ӹ�(A0~J9) : ");
		scanf("%s", str);
	}
	w.head.x=str[0]-'A';
	w.head.y=str[1]-'0';
	printf("\n������ �Ӹ� - %s\n", str);
	printf("�������� ����(0 - ����, 0~7 - �ð����) : ");
	int vec;
	scanf("%d", &vec);
	while(vec<0||vec>7||w.head.x+2*dx[vec]>=MAP_SIZE||w.head.x+2*dx[vec]<0||w.head.y+2*dy[vec]>=MAP_SIZE||w.head.y+2*dy[vec]<0) {
		puts("");
		puts("�߸��� �Է��Դϴ�. Ȥ�� �������� ���� ���� ����ϴ�. �ٽ� �Է����ּ���.");
		printf("�������� �Ӹ� - %s\n", str);
		printf("�������� ����(0 : ����, 0~7 - �ð����) : ");
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
		printf("Player %d�� %d��° �����̸� ����մϴ�.\n", p.id, i);
		print_player_map(p.map);
		worm w=init_worm();
		p.map[w.head.x][w.head.y]='h';
		for(int j=0 ; j<2 ; j++)
			if(p.map[w.body[j].x][w.body[j].y]!='h')
				p.map[w.body[j].x][w.body[j].y]='o';
		p.worm_list.push_back(w);
	}
	//��ź ��� �ڵ�
	system("cls");
	printf("Player %d�� ��ź�� ����մϴ�.\n", p.id);
	print_player_map(p.map);
	char str[101];
	printf("��ź ��ġ(A0~J9) : ");
	scanf("%s", str);
	while(strlen(str)!=2||str[0]<'A'||str[0]>'J'||str[1]<'0'||str[1]>'9'||p.map[str[0]-'A'][str[1]-'0']!=' ') {
		puts("");
		puts("�߸��� �Է��Դϴ�. Ȥ�� ��ź�� �������� �ڸ��� �ֽ��ϴ�. �ٽ� �Է����ּ���.");
		printf("��ź ��ġ(A0~J9) : ");
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
		//�÷��̾ ��ǥ �Է� 
		for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++) {
			system("cls");
			for(int i=0 ; i<50 ; i++)
				printf("=");
			printf("\nTURN : %d\n\n", turn);
			
			
			if(p->gameover) {
				printf("Player %d�� ���ӿ����Դϴ�.\n\n", p->id);
				system("pause");
			} else {
				//�����ڵ�
				printf("Player %d�� ���������Դϴ�.\n\n", p->id);
				print_player_map(p->map);
				printf("���� ��ǥ(A0~J9) : ");
				scanf("%s", str);
				while(strlen(str)!=2||str[0]<'A'||str[0]>'J'||str[1]<'0'||str[1]>'9'||p->map[str[0]-'A'][str[1]-'0']=='X') {
					puts("");
					puts("�߸��� �Է��Դϴ�. Ȥ�� ������ �̹� �����ߴ� ��ǥ�Դϴ�. �ٽ� �Է����ּ���.");
					printf("���� ��ǥ(A0~J9) : ");
					scanf("%s", str);
				}
				q.push({p->id, {str[0]-'A', str[1]-'0'}});
			}
		}
		
		system("cls");
		//���� commit
		while(!q.empty()) {
			
			//������ �����ΰ�?
			pos atk=q.front().second;
			printf("Player %d�� ���� : %c%d\n", q.front().first, atk.x+'A', atk.y);
			q.pop();
			
			for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++) {
				if(p->map[atk.x][atk.y]=='X')
					continue;
						
				//������ 
				for(list<worm>::iterator w=p->worm_list.begin() ; w!=p->worm_list.end() ; w++) {
					if(!poscmp(atk, w->head)) {
						printf("Player %d�� �����̴� ��弦 ���߽��ϴ�.\n", p->id);
						if(p->map[w->body[0].x][w->body[0].y]!='X')
							p->map[w->body[0].x][w->body[0].y]=' ';
						if(p->map[w->body[1].x][w->body[1].y]!='X')
							p->map[w->body[1].x][w->body[1].y]=' ';
						printf("Player %d�� �����̰� �׾����ϴ�.\n", p->id);
						w=p->worm_list.erase(w);
						w--;
					} else if(!poscmp(atk, w->body[0])||!poscmp(atk, w->body[1])) {
						printf("Player %d�� �����̴� �������� �Ծ����ϴ�.\n", p->id);
						w->health--;
						if(!w->health) {
							printf("Player %d�� �����̰� �׾����ϴ�.\n", p->id);
							p->map[w->head.x][w->head.y]=' ';
							w=p->worm_list.erase(w);
							w--;
						}
					}
					
				}
				p->map[atk.x][atk.y]='X';
				
				
				//���ӿ��� �Ǻ�
				if(!p->worm_list.size()&&!p->gameover) {
					printf("Player %d�� ���ӿ����Դϴ�.\n\n", p->id);
					p->gameover=true;
				}
					
				//��ź
				if(!poscmp(atk, p->bomb)) {
					printf("%c%d���� Player %d�� ��ź�� �־����ϴ�.\n\n", atk.x+'A', atk.y, p->id);
					bomb.push({p->id, {atk.x, atk.y}});
					p->bomb.x=-1;
					p->bomb.y=-1;
				}
				
				puts("");
			}
			
			 
		}
		
		//��ź commit
		while(!bomb.empty()) {
			//������ ��ź�ΰ�?
			pos atk=bomb.front().second;
			printf("Player %d�� ��ź : %c%d\n\n", bomb.front().first, atk.x+'A', atk.y);
			bomb.pop();
			
			//��ź ����
			vector<pos> v;
			v.push_back(atk);
			for(int i=0 ; i<8 ; i++) {
				int nx=atk.x+dx[i];
				int ny=atk.y+dy[i];
				if(nx<0||nx>=MAP_SIZE||ny<0||ny>=MAP_SIZE)
					continue;
				v.push_back({nx, ny});
			}
			
			//������ �� �� ����
			for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++) {
				for(list<worm>::iterator w=p->worm_list.begin() ; w!=p->worm_list.end() ; w++)
					for(int i=0 ; i<v.size() ; i++) {
						if(p->map[v[i].x][v[i].y]=='X')
							continue;
						if(!poscmp(v[i], w->head)) {
							printf("Player %d�� �����̴� ��弦 ���߽��ϴ�.\n", p->id);
							if(p->map[w->body[0].x][w->body[0].y]!='X')
								p->map[w->body[0].x][w->body[0].y]=' ';
							if(p->map[w->body[1].x][w->body[1].y]!='X')
								p->map[w->body[1].x][w->body[1].y]=' ';
							printf("Player %d�� �����̰� �׾����ϴ�.\n", p->id);
							w=p->worm_list.erase(w);
							w--;
						} else if(!poscmp(v[i], w->body[0])||!poscmp(v[i], w->body[1])) {
							printf("Player %d�� �����̴� �������� �Ծ����ϴ�.\n", p->id);
							if(!--w->health) {
								printf("Player %d�� �����̰� �׾����ϴ�.\n", p->id);
								p->map[w->head.x][w->head.y]=' ';
								w=p->worm_list.erase(w);
								w--;
							}
						}
						
					}
				for(int i=0 ; i<v.size() ; i++)
					p->map[v[i].x][v[i].y]='X';
					
				if(!p->worm_list.size()&&!p->gameover) {
					printf("Player %d�� ���ӿ����Դϴ�.\n\n", p->id);
					p->gameover=true;
				}
			}
		
			//������ź
			for(list<player>::iterator p=player_list.begin() ; p!=player_list.end() ; p++)
				for(int i=0 ; i<v.size() ; i++)
					if(!poscmp(v[i], p->bomb)) {
						printf("%c%d���� Player %d�� ��ź�� �־����ϴ�.\n", v[i].x+'A', v[i].y, p->id);
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
		printf("������ ���� : Player %d\n", winner);
	else
		puts("�����ڰ� �ƹ��� �����ϴ�.");
}

int main(void) {
	int N;
	
	puts("������ ���� prototype");
	printf("�÷��̾� �� �Է�(2~4) : ");
	scanf("%d", &N);
	
	for(int i=1 ; i<=N ; i++) {
		player p=init_player(i);
		player_list.push_back(p);
	}
	
	game();
	result();
	return 0;
}
