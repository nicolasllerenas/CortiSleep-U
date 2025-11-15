import React, { useState } from 'react'
import MainMenu from '../components/MainMenu'
import ProfilePage from './ProfilePage'
import CheckinsPage from './CheckinsPage'
import POIPage from './POIPage'
import ScreenTimePage from './ScreenTimePage'
import FocusSessionPage from './FocusSessionPage'
import SensoryContentPage from './SensoryContentPage'
import QuestsPage from './QuestsPage'
import RewardsPage from './RewardsPage'

type ViewKey = 'profile' | 'checkins' | 'rewards' | 'poi' | 'focus' | 'screentime' | 'sensory' | 'quests'

const viewMap: Record<ViewKey, React.FC> = {
  profile: ProfilePage,
  checkins: CheckinsPage,
  rewards: RewardsPage,
  poi: POIPage,
  focus: FocusSessionPage,
  screentime: ScreenTimePage,
  sensory: SensoryContentPage,
  quests: QuestsPage,
}

export default function DevGallery() {
  const [view, setView] = useState<ViewKey>('profile')
  const Component = viewMap[view]

  return (
    <div className="space-y-6">
      <h2 className="text-lg font-semibold">Dev Gallery — Main Menu</h2>
      <MainMenu current={view} onNavigate={(v: any) => setView(v)} />

      <section className="p-4 border rounded-lg bg-gray-50 mt-4">
        <h3 className="text-sm font-medium mb-2">Vista: {view}</h3>
        <div className="bg-white p-3 rounded">
          <Component />
        </div>
      </section>
    </div>
  )
}
