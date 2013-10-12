install:
	mvn install -Dmaven.test.skip=true
push:
	hg push https://scm.xhomestudio.org/xhome/xhome-validator
package:clean
	mvn package -Dmaven.test.skip=true
clean:
	mvn clean
deploy:
	mvn deploy -Dmaven.test.skip=true
update:
	hg pull && hg update
