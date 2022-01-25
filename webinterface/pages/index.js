import Head from 'next/head'
import Card from '../components/card'
import CommandsList from '../components/commands'
import Heading from '../components/heading'
import Navbar from '../components/navbar'
import NoSSR from '../components/no_ssr'
import Uptime from '../components/uptime'
import { root_style } from '../js/style'

export default function Home() {
	return (
		<NoSSR>
			<div className="container" style={root_style}>
				<Head>
					<title>Home</title>
					<link rel="icon" href="/favicon.ico" />
					<link rel="stylesheet" href="/style.css" />
				</Head>

				<Navbar />

				<Heading title="BetterNudel" subtitle="The home of better BetterNudel"/>

				<Card>
					<h1>Some information about me</h1>
					<Card>
						<CommandsList small={true} />
					</Card>
					<Card>
						<Uptime />
					</Card>
				</Card>

			</div>
		</NoSSR>
	)
}
