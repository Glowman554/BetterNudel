function build_and_install_plugin {
  echo "Buildind and installing $1"

  (
  cd plugins_src/$1
  mvn package install
  )
}

mvn package install

build_and_install_plugin test_plugin
build_and_install_plugin bmi_plugin
build_and_install_plugin lagersimulation_plugin

mkdir -p plugins/.
cp plugins_src/plugins/* plugins/. -v
