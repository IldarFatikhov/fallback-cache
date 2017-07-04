[![CircleCI](https://circleci.com/gh/IldarFatikhov/fallback-cache/tree/master.svg?style=svg)](https://circleci.com/gh/IldarFatikhov/fallback-cache/tree/master)

# Fallback cache

Develop in Clojure a caching service for an "unstable backend" (you could implement it by your own,
for example you could use https://maps.googleapis.com API with random lags).
Caching service should expose one endpoint:

GET /geocode?address="some address"

which returns and caches all successful responses from backend.
"Unstable backend" might occasionally return 5xx errors or respond slowly.
Errors should not be cached because backend (most probably) will respond successfully next time.
Clients await the response from your service for 2 sec at max.

You should probably pay attention to: 1. The service returns answer in no more than 2 seconds.
2. The service tries to get correct answer from unstable backend "insistently".
3. Caching
4. Scaling

## Usage

docker-compose up

С помощью docker-compose поднимается два экземпляра сервиса. На 80 порту поднимается Nginx балансирующий запросы на инстансы приложения. В качестве кэша используется Redis. 

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
