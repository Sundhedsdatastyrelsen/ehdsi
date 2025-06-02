### Installation af Docker

Installationen af Docker foregår relativt simpelt på Linux servere. Verificer gerne, at alle URL'er i denne vejledning er korrekte inden start, i tilfælde af gamle domæner osv.

#### Forberedelse af serveren

Først gøres serveren klar med eventuelle dependencies til at hente Docker:

```bash
sudo apt update && sudo apt upgrade
sudo apt install -y ca-certificates curl gnupg lsb-release
```

#### Tilføjelse af Dockers GPG-nøgle

Derefter tilføjes Dockers GPG-nøgle, så vi stoler på deres server:

```bash
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | \
  sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
```

#### Tilføjelse af Docker-repository

Tilføj Docker-repository til serveren:

```bash
echo \
  "deb [arch=$(dpkg --print-architecture) \
  signed-by=/etc/apt/keyrings/docker.gpg] \
  https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

#### Installation af Docker

Installer Docker:

```bash
sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io
```

#### Start af Docker

Start Docker:

```bash
sudo systemctl start docker
sudo systemctl enable docker
```

#### Kør Docker som en ikke-root bruger

For at køre Docker som en ikke-root bruger, skal du tilføje din bruger til Docker-gruppen:

```bash
sudo usermod -aG docker $USER
newgrp docker
```

#### Verificering af installationen

For at verificere, at Docker er installeret korrekt, kan du køre følgende kommando:

```bash
docker run hello-world
```

Denne kommando vil downloade et test-image og køre det i en container. Hvis alt er installeret korrekt, vil du se en velkomstbesked fra Docker.

#### Konfiguration af Docker til at starte ved boot

For at sikre, at Docker starter automatisk ved boot, kør:

```bash
sudo systemctl enable docker
```

#### Sikkerhedsopdateringer

Det er vigtigt at holde Docker opdateret for at sikre, at du har de nyeste sikkerhedsopdateringer. Du kan opdatere Docker ved at køre:

```bash
sudo apt update
sudo apt upgrade
```

#### Fejlfinding

Hvis du støder på problemer under installationen, kan du tjekke Docker-loggene for fejlmeddelelser:

```bash
sudo journalctl -u docker
```

Du kan også søge efter løsninger på almindelige problemer på Docker's officielle dokumentation eller på fora som Stack Overflow.

### Tilslutning til TESTA 

For at en server tilsluttes TESTA, skal der indsendes en blanket til godkendelse hos DXC. Hostes serveren hos SIT, skal det klares via dem, og det kan ikke klares via en formular - men skal bruge en dialog, hvor de internt udfylder en formular.

Forbindelsen i dag er sat op med direkte port forwarding fra routeren til vores server - hvilket gør det risikabelt at tage serveren ned, og svært at lave om på. Vi ønsker os en løsning med en load balancer i mellem, men er opmærksomme på at vi måske selv må stå for det.