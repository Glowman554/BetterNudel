import Head from 'next/head'
import Card from '../components/card'
import CommandsList from '../components/commands'
import Heading from '../components/heading'
import NoSSR from '../components/no_ssr'
import Uptime from '../components/uptime'

export default function Home() {
	return (
		<NoSSR>
			<div className="container">
				<Head>
					<title>Create Next App</title>
					<link rel="icon" href="/favicon.ico" />
					<link rel="stylesheet" href="/style.css" />
				</Head>

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
