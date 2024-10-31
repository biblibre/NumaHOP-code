docker_base_img_build_dir = src/main/docker
dc_run_file = run/main/docker/docker-compose.yml
dc_env_file = env/main/docker/docker-compose.yml

docker_run_img_name = numahop-run

dc_cmd_pattern = docker compose -p numahop -f $(1) $(2)

dc_run_cmd = $(call dc_cmd_pattern,$(dc_run_file),$(1))
dc_env_cmd = $(call dc_cmd_pattern,$(dc_env_file),$(1))

mvn = "mvn"

actions = $(1)up $(1)logs $(1)stop $(1)down $(1)clean: $(1)%:

up = up -d
logs = logs -f
stop = stop
down = rm -f -s
clean = $(down) -v

# aliases
all-up: env-up build app-up app-logs
all-stop: app-stop env-stop

build: build-app build-docker

# targets
build-app:
	$(mvn) clean compile -P webapp

build-docker: build-docker-run-img
	$(mvn) -P dev jib:dockerBuild

build-docker-run-img:
	docker build -t $(docker_run_img_name) $(docker_base_img_build_dir) --target run

## app-<action>
$(call actions,app-)
	$(call dc_run_cmd,$($*))

## env-<action>
$(call actions,env-)
	$(call dc_env_cmd,$($*))

local-app: build-app
	$(mvn) spring-boot:run
