import Head from 'next/head'
import Card from '../components/card'
import CommandsList from '../components/commands'
import Heading from '../components/heading'
import Navbar from '../components/navbar'
import NoSSR from '../components/no_ssr'
import { root_style } from '../js/style'

export default function Commands() {
	return (
		<NoSSR>
			<div className="container" style={root_style}>
				<Head>
					<title>Commands</title>
					<link rel="icon" href="/favicon.ico" />
					<link rel="stylesheet" href="/style.css" />
				</Head>

				<Navbar />

				<Heading title="BetterNudel" subtitle="Supported commands!"/>

				<Card>
					<CommandsList small={false} />
				</Card>

			</div>
		</NoSSR>
	)
}
