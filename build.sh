function build_and_install_plugin {
	echo "Building and installing $1"

	(
		cd plugins_src/$1
		mvn package install || (echo "Failed to build $1" && exit 1)
	)
}

mvn package install || (echo "Failed to build bot!" && exit 1)

build_and_install_plugin test_plugin
build_and_install_plugin bmi_plugin
build_and_install_plugin lagersimulation_plugin
build_and_install_plugin exs_plugin
build_and_install_plugin wiki_plugin
build_and_install_plugin rainbow_role_plugin