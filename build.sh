function build_and_install_plugin {
	echo "Building and installing $1"

	(
		cd plugins_src/$1
		mvn package install || exit 1
	)
}

mvn package install || exit 1

#build_and_install_plugin test_plugin
#build_and_install_plugin bmi_plugin
#build_and_install_plugin lagersimulation_plugin
#build_and_install_plugin exs_plugin
#build_and_install_plugin wiki_plugin
build_and_install_plugin rainbow_role_plugin
build_and_install_plugin tic_tac_toe_plugin
