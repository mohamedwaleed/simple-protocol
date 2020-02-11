SHELL = /bin/bash

.PHONY: run test

run:
	@scripts/host/$@ ${ARGS}

test:
	@scripts/host/$@ ${ARGS}
