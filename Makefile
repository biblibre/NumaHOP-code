##############################################
# ================= DOCKER ================= #
##############################################

docker_base_img_build_dir = src/main/docker
dc_run_file = run/main/docker/docker-compose.yml
dc_env_file = env/main/docker/docker-compose.yml
docker_run_img_name = numahop-run

dc_cmd_pattern = docker compose -p numahop -f $(1) $(2)

dc_run_cmd = $(call dc_cmd_pattern,$(dc_run_file),$(1))
dc_env_cmd = $(call dc_cmd_pattern,$(dc_env_file),$(1))

actions = $(1)up $(1)logs $(1)stop $(1)down $(1)clean: $(1)%:


# Available docker actions
up = up -d
logs = logs -f
stop = stop
down = rm -f -s
clean = $(down) -v

# app-<action> run docker action for application only
$(call actions,app-)
	$(call dc_run_cmd,$($*))

# env-<action> run docker action for environnement only
$(call actions,env-)
	$(call dc_env_cmd,$($*))

# Shortcuts
all-up: env-up app-up app-logs
all-stop: app-stop env-stop
all-clean: app-clean env-clean

#############################################
# ================= MAVEN ================= #
#############################################

mvn_cmd_pattern = mvn $(1) -P $(2) -Dfast=true ${MVN_EXTRA_ARGS}
build_targets = $(1)all $(1)docker $(1)webapp $(1)min: $(1)%:

all = docker,webapp
docker = docker
webapp = webapp
min = none


$(call build_targets, build-)
	$(call mvn_cmd_pattern,compile,$($*))

# Build java, front-end, docker and javadoc
full-rebuild: -app-down clean build-all build-docs

clean:
	mvn clean

deep-clean: all-clean clean

fmt:
	mvn spring-javaformat:apply ${MVN_EXTRA_ARGS}

build-docs:
	mvn javadoc:javadoc ${MVN_EXTRA_ARGS}

setup-docker:
	docker build -t $(docker_run_img_name) $(docker_base_img_build_dir) --target run
# 	$(mvn) -P dev jib:dockerBuild ${MVN_EXTRA_ARGS}

local-app: build-app
	mvn spring-boot:run ${MVN_EXTRA_ARGS}


#############################################
# ================= HACK ================= #
#############################################

# Allow to ignore rule dependency failure by adding - before the rule name
-%:
	-@$(MAKE) $*

