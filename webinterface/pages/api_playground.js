import Head from 'next/head'
import ApiPlayground from '../components/api_playground'
import Card from '../components/card'
import Footer from '../components/footer'
import Heading from '../components/heading'
import Navbar from '../components/navbar'
import NoSSR from '../components/no_ssr'
import { root_style } from '../js/style'

export default function Commands() {
	return (
		<NoSSR>
			<div className="container" style={root_style}>
				<Head>
					<title>Api Playground</title>
					<link rel="icon" href="/favicon.ico" />
					<link rel="stylesheet" href="/style.css" />
				</Head>

				<Navbar />

				<Heading title="BetterNudel" subtitle="Api Playground!"/>

				<Card>
					<ApiPlayground />
				</Card>

				<Footer />
			</div>
		</NoSSR>
	)
}
