import Head from 'next/head'
import Card from '../components/card'
import CommandsList from '../components/commands'
import Heading from '../components/heading'
import NoSSR from '../components/no_ssr'

export default function Commands() {
	return (
		<NoSSR>
			<div className="container">
				<Head>
					<title>Create Next App</title>
					<link rel="icon" href="/favicon.ico" />
					<link rel="stylesheet" href="/style.css" />
				</Head>

				<Heading title="BetterNudel" subtitle="Supported commands!"/>

				<Card>
					<CommandsList small={false} />
				</Card>

			</div>
		</NoSSR>
	)
}
