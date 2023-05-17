# Elevator-System
Przykładowy interfejs z polecenia został zmieniony w następujący sposób:
1) Metoda pickup(int, int) na wejściu zamiast (int, int) przyjmuje (int, boolean).
2) Metoda status() zamiast zwracać listę zawierającą (int, int, int), zwraca listę obiektów "Elevator".
3) Usunięta została metoda update(int, int, int). Wszelkie zmiany parametrów windy dokonywane są na liście obiektów "Elevator" przy 
użyciu specjalnych metod.
4) Dodana została metoda chooseFloor(int, int) służąca do wyboru piętra ze środka windy.

Dodatkowo, założone zostało następujące:
1) Ruch windy o 1 piętro zajmuje 1 krok (step).
2) Otwarcie drzwi windy, razem z wysadzeniem i załadowaniem pasażerów również zajmuje 1 krok (step).
3) Każde piętro posiada 2 centralne guziki (1 do jazdy w górę, 1 do jazdy w dół).
4) Gdy na piętrze czekają osoby, a otworzą się drzwi windy jadącej w tą samą stronę co osoby czekające, wsiądą one do 
windy (według kolejności w jakiej pojawiły się na danym piętrze).

Podczas pisania programu, moim głównym celem było wykazanie swoich umiejętności w dziedzinie tworzenia przejrzystego 
i nowoczesnego kodu. Dlatego też udało mi się zaimplementować, w mojej subiektywnej ocenie, rozbudowany system
symulacyjno-testowy, mogący być dobrą podstawą przy tworzeniu projektów. Niestety sprawiło to, że nie zostało 
mi wystarczająco czasu, aby zaimplementować złożony algorytm obsługi wind. Dlatego też zaimplementowany został przeze
mnie najprostszy algorytm FCFS (wg kolejności pięter, na których wzywana zostaje winda).

Dołączam jednak propozycję algorytmu, który byłby znacznie bardziej wydajnym rozwiązaniem:
Przy projektowaniu tego typu systemu trzeba najpierw zastanowić się, pod jakim kątem chcemy go optymalizować. 
W przypadku systemu obsługi wind, wyróżnić można 3 kategorie: czas oczekiwania na wejście do windy, czas oczekiwania i 
czas przejazdu oraz zużycie prądu (minimalna liczba ruchów windy). Sądzę, że najlepiej jest optymalizować czas oczekiwania 
i czas przejazdu, przy równoczesnym zachowaniu pewnego balansu z zużyciem prądu. Dlatego też proponowaną metodą byłaby
Shortest-Seek-Time-First z lekką modyfikacją. Algorytm ten, w przeciwieństwie do First-Come-First-Serve, wybiera windzie
najbliższe dla niej wezwanie (ilość pięter). Zaimplementował bym również funkcję, zatrzymywania się wind, gdy po drodze
napotkają osobę zamierzająca się poruszać w tym samym kierunku co zlecenie. Również dodatkowym mechanizmem, byłoby  
wprowadzenie maksymalnego czasu oczekiwania, po którego przekroczeniu zlecenie otrzymuje priorytet (tzn. pierwsza wolna
winda weźmie to zlecenie).

# Kroki uruchomienia
1) Uruchomić metodę main(String[] args) klasy Main.
2) Następnie wybór odpowiednich opcji odbywa się poprzez wpisanie na klawiaturze odpowiedniej liczby i zatwierdzenie 
enterem.

Wyjaśnienie graficznej reprezentacji systemu:

Pierwsze 2 kolumny pokazują liczbę pasażerów oczekujących na danym piętrze (zielona - pasażerowie jadący do góry, 
czerwona - pasażerowie jadący w dół). Następne każda kolejna kolumna reprezentuje 1 windę, "|" oznacza, że tam 
winda się nie znajduje, "X" oznacza piętro, na których winda planuje się zatrzymać. Sam wagon windy oznaczony jest za 
pomocą liczby (liczba również reprezentuje, liczbę osób aktualnie znajdujących się w windzie, kolor zielony 
oznacza, że winda przyjęła zlecenie, podczas którego po odebraniu pasażera będzie poruszać się w górę [oznacza to, że
najpierw aby dotrzeć do zlecenia może sie zdarzyć, że winda pojedzie w dół], kolor czerwony oznacza sytuację odwrotną).
