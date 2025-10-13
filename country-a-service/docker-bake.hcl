group "default" {
  targets = ["epps-country-a"]
}

target "epps-country-a" {
  dockerfile = "Dockerfile"
  platforms = ["linux/amd64", "linux/arm64"]
  cache-from = [
    "type=registry,ref=ghcr.io/sundhedsdatastyrelsen/ehdsi/epps-country-a:cache"
  ]
  cache-to = [
    "type=registry,ref=ghcr.io/sundhedsdatastyrelsen/ehdsi/epps-country-a:cache"
  ]
  output = [
    "type=image,name=ghcr.io/sundhedsdatastyrelsen/ehdsi/epps-country-a,push=true,tag=latest",
    "type=image,name=eppsregistry.azurecr.io/sundhedsdatastyrelsen/ehdsi/epps-country-a,push=true,tag=latest"
  ]
}