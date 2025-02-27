#########################################################
# ================= Docker Management ================= #
#########################################################

docker_base_img_build_dir = src/main/docker
dc_run_file = run/main/docker/docker-compose.yml
dc_env_file = env/main/docker/docker-compose.yml
docker_run_img_name = numahop-run

dc_cmd_pattern = docker compose -p numahop -f $(1) $(2)

dc_run_cmd = $(call dc_cmd_pattern,$(dc_run_file),$(1))
dc_env_cmd = $(call dc_cmd_pattern,$(dc_env_file),$(1))

actions = $(1)up $(1)logs $(1)stop $(1)down $(1)clean: $(1)%:

# Available docker process actions
up = up -d
logs = logs -f
stop = stop
down = rm -f -s
clean = $(down) -v

# Shortcuts
all-up: env-up app-up
all-stop: app-stop env-stop
all-down: app-down env-down
all-clean: app-clean env-clean

# app-<action> run docker action for application only
$(call actions,app-)
	$(call dc_run_cmd,$($*))

# env-<action> run docker action for environnement only
$(call actions,env-)
	$(call dc_env_cmd,$($*))

# Commands to manage volumes.
dv_cmd_pattern = docker volume rm $(1)

volumes = $(1)db $(1)nh $(1)es : $(1)%:

nh = numahop_numahop-data
db = numahop_numahop-mariadb
es = numahop_numahop-elasticsearch-8

$(call volumes,reset-)
	$(call dv_cmd_pattern,$($*))

# Reset everything.
reset-all: reset-db reset-es reset-nh

###################################################
# ================= Maven Build ================= #
###################################################

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

# List all the Available rules.
.PHONY: list
list:
	@LC_ALL=C $(MAKE) -pRrq -f $(firstword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/(^|\n)# Files(\n|$$)/,/(^|\n)# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | grep -E -v -e '^[^[:alnum:]]' -e '^$@$$'
