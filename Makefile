#!/usr/bin/make -f

# MACHINE examples: et5x00 et6x00 et9x00 dm500hd dm800se dm7020hd dm8000
MACHINE ?= ${subst /,,${subst build-,,${firstword ${dir ${wildcard build-*/}}}}}

ifeq "$(MACHINE)" ""
	MACHINE=et9x00
endif

# Adjust according to the number CPU cores to use for parallel build.
# Default: Number of processors in /proc/cpuinfo, if present, or 1.
NR_CPU := $(shell [ -f /proc/cpuinfo ] && grep -c '^processor\s*:' /proc/cpuinfo || echo 1)
BB_NUMBER_THREADS ?= $(NR_CPU)
PARALLEL_MAKE ?= -j $(NR_CPU)

XSUM ?= md5sum

BUILD_DIR = $(CURDIR)/build-$(MACHINE)
TOPDIR = $(BUILD_DIR)
DL_DIR = $(CURDIR)/sources
SSTATE_DIR = $(TOPDIR)/sstate-cache
TMPDIR = $(TOPDIR)/tmp
DEPDIR = $(TOPDIR)/.deps

BBLAYERS ?= \
	$(CURDIR)/meta-openembedded/meta-oe \
	$(CURDIR)/openembedded-core/meta \
	$(CURDIR)/meta-openpli \

CONFFILES = \
	$(TOPDIR)/env.source \
	$(TOPDIR)/conf/openpli.conf \
	$(TOPDIR)/conf/bblayers.conf \
	$(TOPDIR)/conf/local.conf \
	$(TOPDIR)/conf/site.conf

CONFDEPS = \
	$(DEPDIR)/.env.source.$(BITBAKE_ENV_HASH) \
	$(DEPDIR)/.openpli.conf.$(OPENPLI_CONF_HASH) \
	$(DEPDIR)/.bblayers.conf.$(MACHINE).$(BBLAYERS_CONF_HASH) \
	$(DEPDIR)/.local.conf.$(MACHINE).$(LOCAL_CONF_HASH)

GIT ?= git
GIT_REMOTE := $(shell $(GIT) remote)
GIT_USER_NAME := $(shell $(GIT) config user.name)
GIT_USER_EMAIL := $(shell $(GIT) config user.email)

hash = $(shell echo $(1) | $(XSUM) | awk '{print $$1}')

.DEFAULT_GOAL := all
all: init
	@echo
	@echo "Openembedded for the OpenPLi 3.0 environment has been initialized"
	@echo "properly. Now you can start building your image, by doing either:"
	@echo
	@echo " MACHINE=$(MACHINE) make image"
	@echo "	or"
	@echo " cd $(BUILD_DIR) ; source env.source ; bitbake openpli-enigma2-image"
	@echo

$(BBLAYERS):
	[ -d $@ ] || $(MAKE) $(MFLAGS) update

initialize: init

init: $(BBLAYERS) $(CONFFILES)

image: init
	@echo 'Building image for $(MACHINE)'
	@. $(TOPDIR)/env.source && cd $(TOPDIR) && bitbake openpli-enigma2-image

update:
	@echo 'Updating Git repositories...'
	@HASH=`$(XSUM) $(MAKEFILE_LIST)`; \
	if [ -n "$(GIT_REMOTE)" ]; then \
		$(GIT) pull --ff-only || $(GIT) pull --rebase; \
	fi; \
	if [ "$$HASH" != "`$(XSUM) $(MAKEFILE_LIST)`" ]; then \
		echo 'Makefile changed. Restarting...'; \
		$(MAKE) $(MFLAGS) --no-print-directory $(MAKECMDGOALS); \
	else \
		$(GIT) submodule sync && \
		$(GIT) submodule update --init && \
		echo "The openpli OE is now up-to-date."; \
	fi

.PHONY: all image init initialize update usage

BITBAKE_ENV_HASH := $(call hash, \
	'BITBAKE_ENV_VERSION = "0"' \
	'CURDIR = "$(CURDIR)"' \
	)

$(TOPDIR)/env.source: $(DEPDIR)/.env.source.$(BITBAKE_ENV_HASH)
	@echo 'Generating $@'
	@echo 'export PATH=$(CURDIR)/openembedded-core/scripts:$(CURDIR)/bitbake/bin:$${PATH}' >> $@

OPENPLI_CONF_HASH := $(call hash, \
	'OPENPLI_CONF_VERSION = "1"' \
	'CURDIR = "$(CURDIR)"' \
	'BB_NUMBER_THREADS = "$(BB_NUMBER_THREADS)"' \
	'PARALLEL_MAKE = "$(PARALLEL_MAKE)"' \
	'DL_DIR = "$(DL_DIR)"' \
	'SSTATE_DIR = "$(SSTATE_DIR)"' \
	'TMPDIR = "$(TMPDIR)"' \
	)

$(TOPDIR)/conf/openpli.conf: $(DEPDIR)/.openpli.conf.$(OPENPLI_CONF_HASH)
	@echo 'Generating $@'
	@test -d $(@D) || mkdir -p $(@D)
	@echo 'SSTATE_DIR = "$(SSTATE_DIR)"' >> $@
	@echo 'TMPDIR = "$(TMPDIR)"' >> $@
	@echo 'BB_GENERATE_MIRROR_TARBALLS = "0"' >> $@
	@echo 'BBINCLUDELOGS = "yes"' >> $@
	@echo 'CONF_VERSION = "1"' >> $@
	@echo 'DISTRO = "openpli"' >> $@
	@echo 'EXTRA_IMAGE_FEATURES = "debug-tweaks"' >> $@
	@echo 'USER_CLASSES = "buildstats"' >> $@
	@if [ -f "$(CURDIR)/branding.conf" ]; then \
		echo "### BRANDING" >> $@; \
		cat $(CURDIR)/branding.conf >> $@; \
	fi

LOCAL_CONF_HASH := $(call hash, \
	'LOCAL_CONF_VERSION = "0"' \
	'CURDIR = "$(CURDIR)"' \
	'TOPDIR = "$(TOPDIR)"' \
	'MACHINE = "$(MACHINE)"' \
	)

$(TOPDIR)/conf/local.conf: $(DEPDIR)/.local.conf.$(MACHINE).$(LOCAL_CONF_HASH)
	@echo 'Generating $@'
	@test -d $(@D) || mkdir -p $(@D)
	@echo 'TOPDIR = "$(TOPDIR)"' > $@
	@echo 'MACHINE = "$(MACHINE)"' >> $@
	@echo 'require $(TOPDIR)/conf/openpli.conf' >> $@

$(TOPDIR)/conf/site.conf: $(CURDIR)/site.conf
	@ln -s ../../site.conf $@

$(CURDIR)/site.conf:
	@echo 'SCONF_VERSION = "1"' >> $@
	@echo 'BB_NUMBER_THREADS = "$(BB_NUMBER_THREADS)"' >> $@
	@echo 'PARALLEL_MAKE = "$(PARALLEL_MAKE)"' >> $@
	@echo 'BUILD_OPTIMIZATION = "-march=native -O2 -pipe"' >> $@
	@echo 'DL_DIR = "$(DL_DIR)"' >> $@
	@echo 'INHERIT += "rm_work"' >> $@

BBLAYERS_CONF_HASH := $(call hash, \
	'BBLAYERS_CONF_VERSION = "0"' \
	'CURDIR = "$(CURDIR)"' \
	'BBLAYERS = "$(BBLAYERS)"' \
	)

$(TOPDIR)/conf/bblayers.conf: $(DEPDIR)/.bblayers.conf.$(MACHINE).$(BBLAYERS_CONF_HASH)
	@echo 'Generating $@'
	@test -d $(@D) || mkdir -p $(@D)
	@echo 'LCONF_VERSION = "4"' >> $@
	@echo 'BBFILES = ""' >> $@
	@echo 'BBLAYERS = "$(BBLAYERS)"' >> $@

$(CONFDEPS):
	@test -d $(@D) || mkdir -p $(@D)
	@$(RM) $(basename $@).*
	@touch $@
