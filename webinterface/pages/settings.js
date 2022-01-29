import Head from 'next/head'
import Button from '../components/button'
import Card from '../components/card'
import Footer from '../components/footer'
import Heading from '../components/heading'
import Navbar from '../components/navbar'
import NoSSR from '../components/no_ssr'
import { reset_color, root_style, set_color } from '../js/style'

export default function Shell_() {

	return (
		<NoSSR>
			<div className="container" style={root_style}>
				<Head>
					<title>Settings</title>
					<link rel="icon" href="/favicon.ico" />
					<link rel="stylesheet" href="/style.css" />
				</Head>

				<Navbar title="Settings" />

				<Heading title="Settings" subtitle="Settings for this website" />

				<Card>
					<h2>Color theme</h2>

					<Button onClick={
						event => {
							reset_color();
						}
					}>Reset color's</Button>
					<br />
					<br />

					<Button onClick={
						event => {
							set_color("black", "dark-grey");
						}
					}>Load black theme</Button>

					<br />
					<Button onClick={
						event => {
							set_color("grey", "white");
						}
					}>Load white theme</Button>	

					<br />
					<Button onClick={
						event => {
							set_color("black", "black");
						}
					}>Load ultra black theme</Button>

					<br />
					<Button onClick={
						event => {
							set_color("white", "white");
						}
					}>Load ultra white theme</Button>

					<br />
					<Button onClick={
						event => {
							set_color("black", "pink");
						}
					}>Load pink theme</Button>
				</Card>

				<Card>
					<h2>Delete all settings</h2>
					<Button onClick={
						event => {
							localStorage.clear();
							location.reload();
						}
					}>Delete all settings</Button>
				</Card>

				<Card>
					<h2>Change api location</h2>
					<Button onClick={
						event => {
							var new_location = prompt("Enter new api location");
							new_location = new_location.endsWith("/") ? new_location.slice(0, new_location.length - 1) : new_location;
							localStorage.setItem("api_path", new_location);
							location.reload();
						}
					}>Change api location</Button>
				</Card>

				<Footer />

			</div>
		</NoSSR>
	)
}
