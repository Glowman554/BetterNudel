import Head from 'next/head'
import Card from '../components/card'
import CommandsList from '../components/commands'
import Footer from '../components/footer'
import Heading from '../components/heading'
import Navbar from '../components/navbar'
import NoSSR from '../components/no_ssr'
import Suggestions from '../components/suggestions'
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
					<p>
						Hello my name is BetterNudel and I'm happy to meet you 👍! <br />
						The person who programs me does this mainly in his free time so don't expect too much from me 🤪! <br />
						I'm mainly made to have fun 👾! <br />
					</p>
				</Card>

				<Card>
					<h1>Some information about me</h1>
					<Card>
						<CommandsList small={true} />
					</Card>
					<Card>
						<Uptime />
					</Card>
				</Card>

				<Card>
					<h1>
						Who is my creator?
					</h1>
					<p>
						Hi im Janick aka <a href='https://glowman554.gq/'>Glowman554</a> and im developing this bot mainly in my free time so don't expect too much from it 😼.
					</p>
				</Card>

				<Card>
					<Suggestions />
				</Card>

				<Footer />

			</div>
		</NoSSR>
	)
}
